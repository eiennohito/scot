package org.eiennohito.scot.bot.message

import akka.actor.ActorRef
import java.util.Date
import org.eiennohito.scot.info.ConferenceInfo
import us.troutwine.barkety.jid.MucJID

/**
 * @author eiennohito
 * @since 24.11.11 
 */

case class MessageHeader(rcvDate: Date, confProcessor: ActorRef, mjid: MucJID)

case class Envelope[T](info: MessageHeader, msg: T)