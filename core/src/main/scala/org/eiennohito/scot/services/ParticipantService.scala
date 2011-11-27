package org.eiennohito.scot.services

import akka.stm.TransactionalMap
import akka.stm.atomic
import org.eiennohito.scot.model.{Participant, UserInfo}
import org.eiennohito.scot.info.ConferenceInfo
import us.troutwine.barkety.jid.MucJID

/**
 * @author eiennohito
 * @since 17.11.11 
 */

object ParticipantService {
  private val cached = TransactionalMap[String, RoomParticipantCache]

  def lookupUser(mjid: MucJID) : Option[Participant] = {
    None
  }

  def cacheUser(user: Participant) {

  }

}