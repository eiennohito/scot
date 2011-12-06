package org.eiennohito.scot.services

import java.util.Date
import org.eiennohito.scot.bot.message.MessageHeader
import us.troutwine.barkety.{ExtendedUserInfo, UserInfoRequest}
import us.troutwine.barkety.jid.{JID, MucJID}
import java.lang.String
import net.liftweb.mongodb.BsonDSL._
import org.eiennohito.scot.model.{ChangeNickEvent, Participant}

/**
 * @author eiennohito
 * @since 27.11.11 
 */

trait ParticipantResolverPresent {
  val resolver: ParticipantResolver
}

trait MongoParticipantResolverPresent extends ParticipantResolverPresent {
  override val resolver = new MongoParticipantResolver with HasMongoConfigurator {}
}

trait ParticipantResolver {
  def resolve(date: Date, messageHeader: MessageHeader) : Participant
}

trait MongoParticipantResolver extends ParticipantResolver with HasConfigurator {

  def participantByMjid(mjid: MucJID, date: Date): Participant = {
    lookup(mjid) match {
      case Some(x) => x
      case None => {
        register(mjid, mjid, date, Some(mjid.nickname))
      }
    }
  }

  def lookup(jid: String): Option[Participant] = {
    import com.foursquare.rogue.Rogue._
    //Participant.find("mjid" -> jid)
    val list = Participant where  (_.jid eqs jid) fetch()
    list.headOption
  }

  def register(jid: String, mjid: MucJID, date: Date, nick: Option[String] = None): Participant = {
    val p = Participant.createRecord
      .jid(jid).nick(nick)
      .conf(configurator.loadConferenceConfig(mjid).id.is).save
    //registerNickChange(p, mjid.nickname, date)
    p
  }

  def lookupParticipant(jid: JID, mjid: MucJID, date: Date): Option[Participant] = {
    lookup(jid) match {
      case x : Some[Participant] => x
      case None => {
        Some(register(jid, mjid, date))
      }
    }    
  }

  def registerNickChange(participant: Participant, newNick: String, date: Date) {
    val old = participant.nick.valueBox
    ChangeNickEvent.createRecord
      .from(old).to(newNick).who(participant.id.is).when(date).save
    participant.nick(newNick).save
  }

  def resolve(date: Date, hdr: MessageHeader) = {
    val mjid = hdr.mjid
    ParticipantService.lookupUser(mjid) match {
      case Some(x) => x
      case None => {
        val ui = (hdr.confProcessor ? UserInfoRequest(mjid)).as[Option[ExtendedUserInfo]].getOrElse(None)
        val p_box = ui flatMap (_.jid) flatMap (lookupParticipant(_, mjid, date))
        val p = p_box getOrElse participantByMjid (mjid, date)
        
        if (p.nick.valueBox.isEmpty || p.nick.is != mjid.nickname) {
          registerNickChange(p, mjid.nickname, date)
        }
        p
      }
    }
  }
}