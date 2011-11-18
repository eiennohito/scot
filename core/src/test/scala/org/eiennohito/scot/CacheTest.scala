package org.eiennohito.scot

import model.Participant
import services.RoomParticipantCache

/**
 * @author eiennohito
 * @since 17.11.11 
 */

class CacheTest extends org.scalatest.FunSuite with org.scalatest.matchers.ShouldMatchers {

  test("Cache should return added val") {
    val c = new RoomParticipantCache("fl-talks", "c.j.r")
    c.cache(Participant.createRecord.nick("something"))
    c.lookup("something") should not be None
  }

  test("cache should not have that value") {
    val c = new RoomParticipantCache("fl-talks", "c.j.r")
    c.cache(Participant.createRecord.nick("something"))
    c.isCached("anything") should be (false)
  }

}