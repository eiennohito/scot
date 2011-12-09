package org.eiennohito.scot.bot.logging

import com.weiglewilczek.slf4s.Logging
import akka.actor.Actor
import us.troutwine.barkety.MucPresence
import org.jivesoftware.smack.packet.Presence.Type
import us.troutwine.barkety.jid.MucJID
import java.util.Date
import akka.config.Supervision.OneForOneStrategy
import org.eiennohito.scot.bot.message.{Envelope, MessageHeader}
import org.eiennohito.scot.model.OnlineTime
import net.liftweb.mongodb.BsonDSL._
import org.eiennohito.scot.services.HasParticipantResolver
import net.liftweb.json
import json.DefaultFormats

/**
 * @author eiennohito
 * @since 26.11.11 
 */

abstract class PresenceLogger extends Actor with Logging with HasParticipantResolver {
  self.faultHandler = new OneForOneStrategy(List(classOf[Exception]))
  implicit private val formats = DefaultFormats.lossless

  def somebodyLoggedIn(mjid: MucJID, date: Date, info: MessageHeader) {
    val p = resolver.resolve(date, info)
    val ot = OnlineTime.createRecord
    ot.who(p.id.is).cameOnline(date).save
  }

  def somebodyLoggedOut(mjid: MucJID, date: Date, info: MessageHeader) {
    val p = resolver.resolve(date, info)
    //OnlineTime where (_.who eqs id)
    val q = ("who" -> p.id.is) ~ ("wentOffline" -> json.JNull)
    OnlineTime.update(q, ("$set" -> ("wentOffline" -> date)))
  }

  protected def receive = {
    case e: Envelope[MucPresence] => {
      val m = e.msg
      m.status match {
        case Type.available => somebodyLoggedIn(m.mjid, m.time, e.info)
        case Type.unavailable => somebodyLoggedOut(m.mjid, m.time, e.info)
        case _ => logger.info("invalid status %s".format(m.status))
      } 
    }
  }
}