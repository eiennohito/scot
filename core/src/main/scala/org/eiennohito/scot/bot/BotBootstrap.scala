package org.eiennohito.scot.bot

import net.liftweb.util.Props
import akka.actor.Actor._
import us.troutwine.barkety.{JID, ChatSupervisor}
import akka.actor.ActorRef

/**
 * @author eiennohito
 * @since 17.11.11 
 */

object BotBootstrap {

  var chatsup : ActorRef = _



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