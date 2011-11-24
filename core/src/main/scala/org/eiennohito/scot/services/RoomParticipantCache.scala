package org.eiennohito.scot.services

import org.eiennohito.scot.model.Participant
import akka.stm.TransactionalMap
import akka.stm.atomic

/**
 * @author eiennohito
 * @since 17.11.11 
 */

class RoomParticipantCache(roomName: String, server:String) {
  import StmManager.readOnly
  private val cached = new TransactionalMap[String, Participant]


  def cache(p: Participant) {
    atomic {
      cached += p.nick.is -> p
    }
  }

  def lookup(name: String) : Option[Participant] = {
    atomic (readOnly) { cached.get(name) }
  }

  def isCached(name: String) = {
    atomic (readOnly) { cached.get(name) } match {
      case None => false
      case Some(_) => true
    }
  }

  override def toString = "%s@%s(%d)".format(roomName, server, cached.size)
}