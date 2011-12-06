package org.eiennohito.scot.services

import org.eiennohito.scot.bot.message.MessageHeader
import java.util.Date
import us.troutwine.barkety.jid.MucJID
import akka.actor.Actor
import us.troutwine.barkety.UserInfoRequest
import akka.testkit.TestActorRef
import org.eiennohito.scot.mongo.SuiteWithDb
import org.eiennohito.scot.info.ConferenceInfo
import org.scalatest.{BeforeAndAfterEach, BeforeAndAfterAll}
import org.eiennohito.scot.model.{Participant, ConferenceEntry}
import org.scalatest.matchers.ShouldMatchers

/**
 * @author eiennohito
 * @since 06.12.11 
 */

class MockUserResolver extends Actor {
  protected def receive = {
    case ui : UserInfoRequest => self.tryReply(None)
  }
}

class ParticipantResolverTest extends SuiteWithDb with ShouldMatchers with BeforeAndAfterEach {
  
  val p = new MongoParticipantResolver {
        val configurator = new Configurator {
          def loadConferenceConfig(ci: ConferenceInfo) = ConferenceEntry.createRecord.save
        }
      }
  
    
  test("bymjid works as planned") {
    val mjid = MucJID("test", "test", "test")
    val time = new Date
    val p1 = p.participantByMjid(mjid, time)
    val p2 = p.participantByMjid(mjid, time)
    p1.id.is should equal (p2.id.is)
  }
    
  test("should get somebody with same mjid if present") {    
    val actor = TestActorRef[MockUserResolver].start()
    val p1 = p.resolve(new Date, MessageHeader(new Date, actor, MucJID("test", "test", "test")))
    val p2 = p.resolve(new Date, MessageHeader(new Date, actor, MucJID("test", "test", "test")))
    p1.id.is should equal (p2.id.is)
    actor.stop()
  }

  override protected def afterEach() {
    import com.foursquare.rogue.Rogue._
    Participant where (_.nick eqs "test") bulkDelete_!!()
  }
}