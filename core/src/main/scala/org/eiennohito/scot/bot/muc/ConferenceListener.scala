package org.eiennohito.scot.bot.muc

import org.eiennohito.scot.bot.CoreActors
import org.eiennohito.scot.messages.Message
import us.troutwine.barkety.jid.{JID, MucJID}
import akka.actor.{ActorRef, Actor}
import us.troutwine.barkety._
import com.weiglewilczek.slf4s.Logging
import java.util.Date
import org.eiennohito.scot.bot.message.{Envelope, MessageHeader}
import org.eiennohito.scot.services.HasParticipantResolver
import org.eiennohito.scot.info.ConferenceLoginInfo
import net.liftweb.common.Full

/**
 * @author eiennohito
 * @since 24.11.11 
 */

abstract class ConferenceListener(ca: CoreActors, ci: ConferenceLoginInfo) extends Actor with Logging with HasParticipantResolver {

  self.id = "ConfList:" + ci

  override def preStart() {
    chatRoom ! RegisterParent(self)
  }

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
    case ui: UserInfoRequest => chatRoom forward ui
  }


}

