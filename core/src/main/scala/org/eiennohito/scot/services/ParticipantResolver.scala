package org.eiennohito.scot.services

import us.troutwine.barkety.jid.MucJID
import java.util.Date
import org.eiennohito.scot.bot.message.MessageHeader
import org.eiennohito.scot.model.Participant

/**
 * @author eiennohito
 * @since 27.11.11 
 */

trait ParticipantResolverPresent {
  val resolver: ParticipantResolver
}

trait MongoParticipantResolverPresent extends ParticipantResolverPresent {
  override val resolver = new MongoParticipantResolver {}
}

trait ParticipantResolver {
  def resolve(mjid: MucJID, date: Date, messageHeader: MessageHeader) : Participant
}

trait MongoParticipantResolver extends ParticipantResolver {
  def resolve(mjid: MucJID, date: Date, messageHeader: MessageHeader) = {
    null
  }
}