package org.eiennohito.scot.app

import us.troutwine.barkety._
import akka.actor.{ActorRef, Actor}
import Actor.actorOf
import org.eiennohito.scot.bot.BotBootstrap
import org.eiennohito.scot.info.ConferenceLoginInfo
import org.eiennohito.scot.model.ConferenceEntry
import net.liftweb.mongodb.BsonDSL._
import org.eiennohito.scot.db.DbInitializer


/**
 * @author eiennohito
 * @since 17.11.11 
 */


class Acty(child:ActorRef) extends Actor {
  child ! RegisterParent(self)

  def receive = {
    case InboundMessage(msg:String) => println(msg)
  }
}



object BotMain extends App {
  
  def tryo[A](fnc: => A) : Option[A] = {
    try {
      Some(fnc)
    } catch {
      case _ => None
    }
  }
  
  override def main(args: Array[String]) {
    DbInitializer.init()
    BotBootstrap.launch()
    val ci = ConferenceLoginInfo("bot_test3", "conference.jabber.ru", "test-bot", None)
    val l = tryo {
      ConferenceEntry.findAll("room" -> "bot_test3")
    } getOrElse Nil
    if (l.size == 0) {
      ConferenceEntry.createRecord
        .room(ci.room)
        .server(ci.server)
        .usingNick(ci.nickname)
        .password(ci.password)
        .save
    }
    val room = BotBootstrap.loginToRoom(ci)
    val acty = actorOf(new Acty(room)).start()
    room ! "hello, my name is bot, i'm testing sending messages to MUC"

    System.in.read()
    BotBootstrap.stop()
    acty.stop()
  }
}
