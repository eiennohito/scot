package org.eiennohito.scot.bot

import logging.PresenceLogger
import akka.actor.Actor._
import message.{MessageHeader, Envelope}
import us.troutwine.barkety.MucPresence
import org.eiennohito.scot.services.ParticipantResolver
import us.troutwine.barkety.jid.MucJID
import java.util.Date
import org.jivesoftware.smack.packet.Presence
import org.eiennohito.scot.model.{OnlineTime, Participant}
import net.liftweb.json.JsonDSL._
import net.liftweb.common.Empty
import org.scalatest.BeforeAndAfter
import org.eiennohito.scot.mongo.TestDb
import net.liftweb.json
import akka.testkit.{TestKit, TestActorRef}
import akka.util.duration._

/**
 * @author eiennohito
 * @since 27.11.11 
 */

class PresenceLoggerTest extends org.scalatest.FunSuite with org.scalatest.matchers.ShouldMatchers with BeforeAndAfter
  with TestDb with TestKit {

  test("presence logger logs ok") {
    val p = Participant.createRecord.save
    val pl = TestActorRef(new PresenceLogger {
      val resolver = new ParticipantResolver {
        def resolve(mjid: MucJID, date: Date, messageHeader: MessageHeader) = p
      }
    }).start()
    
    pl ! Envelope[MucPresence](MessageHeader(new Date, null), 
      MucPresence(MucJID("fl-talks", "c.j.r", "who"), Presence.Type.available, new Date))

    expectNoMsg(10 millis)

    val q = ("who" -> p.id.is) ~ ("wentOffline" -> json.JNull)
    val b = OnlineTime.find(q)
    b should not be Empty
    b.get.who.is should equal (p.id.is)

    pl ! Envelope[MucPresence](MessageHeader(new Date, null),
          MucPresence(MucJID("fl-talks", "c.j.r", "who"), Presence.Type.unavailable, new Date))
    expectNoMsg(10 millis)
    val b2 = OnlineTime.find(b.get.id.is)
    b2 should not be Empty
    b2.get.wentOffline.valueBox should not be Empty
    pl.stop()
  }
  
  

}