package org.eiennohito.scot.bot

import net.liftweb.util.Props
import akka.actor.Actor._
import akka.actor.ActorRef
import us.troutwine.barkety.jid.JID
import us.troutwine.barkety.{JoinRoom, ChatSupervisor}

/**
 * @author eiennohito
 * @since 17.11.11 
 */

object BotBootstrap {

  var chatsup : ActorRef = _
  
  
  def loginToRoom(name: String, user: String, password: Option[String] = None) = {
    val fut = chatsup ? JoinRoom(JID(name), Some(user), password)
    fut.as[ActorRef]
  }



  def launch() {
    val jid = JID(Props.get("bot.jid").get)
    val passwd = Props.get("bot.passwd").get
    chatsup = actorOf(new ChatSupervisor(jid, passwd))
    chatsup.start()
  }

  def stop() {
    chatsup.stop()
  }
}