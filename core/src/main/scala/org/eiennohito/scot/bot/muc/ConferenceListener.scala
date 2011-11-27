package org.eiennohito.scot.bot.muc

import org.eiennohito.scot.bot.CoreActors
import org.eiennohito.scot.messages.Message
import org.eiennohito.scot.services.ParticipantService
import org.eiennohito.scot.model.ConferenceEntry
import us.troutwine.barkety.jid.{JID, MucJID}
import akka.actor.{ActorRef, Actor}
import us.troutwine.barkety._
import org.slf4j.LoggerFactory
import com.weiglewilczek.slf4s.Logging
import java.util.Date
import org.eiennohito.scot.bot.message.{Envelope, MessageHeader}

/**
 * @author eiennohito
 * @since 24.11.11 
 */

class ConferenceListener(ca: CoreActors, ci: ConferenceEntry) extends Actor with Logging {
  
  val l = LoggerFactory.getLogger(classOf[ConferenceListener]) 
  
  private val chatRoom = (ca.chatSupervisor ? JoinRoom(
    JID(ci.conferenceName.is, ci.conferenceServer.is),  
    ci.usingNick.valueBox,
    ci.password.valueBox)).as[ActorRef].get
  
  chatRoom ! RegisterParent(self)

  def locateInRoom(mjid: MucJID): Option[String] = {
    val ui = (chatRoom ? UserInfoRequest(mjid)).as[ExtendedUserInfo]
    ui flatMap  {u => u.jid.map(_.toString)}
  }

  def lookupJid(mjid: MucJID): Option[String] = {
    ParticipantService.lookupUser(mjid) match {
      case Some(x) => Some(x.jid.is)
      case None => locateInRoom(mjid)
    }
  }

  def processRawMessage(m: MucMessage): Envelope[Message] = {
    Envelope[Message](
      MessageHeader(
        new Date,
        self
      ),
      Message(
        m.jid.nickname,
        lookupJid(m.jid),
        m.time,
        m.msg
      )
    )
  }
  
  protected def receive = {
    case msg: MucMessage => {
      ca.mainRouter ! processRawMessage(msg)
    }
    case p: MucPresence => {
      ca.presenceLogger ! Envelope[MucPresence](MessageHeader(new Date, self), p)
    }
  }
}

