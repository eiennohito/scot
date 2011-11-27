package org.eiennohito.scot.bot.message

import org.eiennohito.scot.bot.CoreActors
import org.eiennohito.scot.messages.Message
import org.eiennohito.scot.services.ParticipantService
import org.eiennohito.scot.model.ConferenceEntry
import us.troutwine.barkety.jid.{JID, MucJID}
import akka.actor.{ActorRef, Actor}
import us.troutwine.barkety._
import org.slf4j.LoggerFactory
import org.jivesoftware.smack.packet.Presence
import com.weiglewilczek.slf4s.Logging
import java.util.Date

/**
 * @author eiennohito
 * @since 24.11.11 
 */

case class MessageHeader(rcvDate: Date, confProcessor: ActorRef)

case class Envelope[T](info: MessageHeader, msg: T)
