package org.eiennohito.scot.bot.logging

import akka.actor.Actor
import com.weiglewilczek.slf4s.Logging
import us.troutwine.barkety.MucMessage
import org.eiennohito.scot.services.{ConfigurationService, ParticipantResolverPresent}
import org.eiennohito.scot.bot.message.{MessageHeader, Envelope}
import org.eiennohito.scot.model.{ LogEntry => DbMsg}
import org.eiennohito.scot.messages.Message
import us.troutwine.barkety.jid.MucJID
import org.eiennohito.scot.info.ConferenceInfo

/**
 * @author eiennohito
 * @since 17.11.11 
 */

abstract class MessageLogger extends Actor with Logging with ParticipantResolverPresent {

  def logMessage(header: MessageHeader, msg: Message) {
    val conf = ConfigurationService.loadConferenceConfig(header.mjid)
    val p = resolver.resolve(msg.time, header)
    DbMsg.createRecord
      .who(p.id.is).conf(conf.id.is)
      .time(msg.time).nick(header.mjid.nickname).content(msg.msg).save
  }

  protected def receive = {
    case e: Envelope[Message] => logMessage(e.info, e.msg)
  }
}