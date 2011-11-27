package org.eiennohito.scot.bot.muc

import org.eiennohito.scot.bot.CoreActors
import org.eiennohito.scot.messages.Message
import org.eiennohito.scot.model.ConferenceEntry
import us.troutwine.barkety.jid.{JID, MucJID}
import akka.actor.{ActorRef, Actor}
import us.troutwine.barkety._
import org.slf4j.LoggerFactory
import com.weiglewilczek.slf4s.Logging
import java.util.Date
import org.eiennohito.scot.bot.message.{Envelope, MessageHeader}
import org.eiennohito.scot.services.{ParticipantResolverPresent, ParticipantService}
import org.eiennohito.scot.info.{ConferenceLoginInfo, ConferenceInfo}
import net.liftweb.common.Full

/**
 * @author eiennohito
 * @since 24.11.11 
 */

abstract class ConferenceListener(ca: CoreActors, ci: ConferenceLoginInfo) extends Actor with Logging with ParticipantResolverPresent {
  override def preStart() {
    chatRoom ! RegisterParent(self)
  }

  val l = LoggerFactory.getLogger(classOf[ConferenceListener])
  val info = ConferenceInfo(ci.room, ci.server)
  
  private val chatRoom = (ca.chatSupervisor ? JoinRoom(
      JID(ci.room, ci.server), Full(ci.nickname), ci.password))
      .as[ActorRef].get


  def header(mjid: MucJID) = MessageHeader(
          new Date,
          self,
          mjid
        )


  def processRawMessage(m: MucMessage): Envelope[Message] = {
    Envelope[Message](
      header(m.mjid),
      Message(
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
      ca.presenceLogger ! Envelope[MucPresence](header(p.mjid), p)
    }
    case s: String => chatRoom ! s
    case ui: UserInfoRequest => chatRoom ! ui
  }
}

