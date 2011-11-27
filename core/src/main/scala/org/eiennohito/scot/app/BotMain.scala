package org.eiennohito.scot.app

import us.troutwine.barkety._
import akka.actor.{ActorRef, Actor}
import Actor.actorOf
import org.eiennohito.scot.bot.BotBootstrap


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
  override def main(args: Array[String]) {
/*
    (chatsup ? CreateChat(JID(""))).as[ActorRef] match {
      case Some(chatter) =>
        actorOf(new Acty(chatter)).start
        chatter ! OutboundMessage("Hi, you!")
      case None =>
    }
*/
    BotBootstrap.launch()
    val room = BotBootstrap.loginToRoom("bot_test3@conference.jabber.ru", "test-bot")
    val acty = actorOf(new Acty(room.get)).start()
    room ! "hello, my name is bot, i'm testing sending messages to MUC"
    System.in.read()
  }
}
