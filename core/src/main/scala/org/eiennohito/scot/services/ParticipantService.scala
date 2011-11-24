package org.eiennohito.scot.services

import akka.stm.TransactionalMap
import akka.stm.atomic
import org.eiennohito.scot.model.{Participant, UserInfo}

/**
 * @author eiennohito
 * @since 17.11.11 
 */

object ParticipantService {
  private val cached = TransactionalMap[String, RoomParticipantCache]

  def lookupUser(nick: String, room: String) : Option[UserInfo] = {
    None
  }

  def cacheUser(user: Participant) {

  }

}