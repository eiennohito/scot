package org.eiennohito.scot.bot.actors

import org.eiennohito.scot.messages.Message
import org.eiennohito.scot.bot.message.{MessageHeader, Envelope}
import akka.actor.{ActorRef, Actor}
import org.eiennohito.scot.plugins.Plugin

/**
 * @author eiennohito
 * @since 27.11.11 
 */

class MainRouter(logger: ActorRef, plugins: List[Plugin]) extends Actor {

  def locatePrefix(msg: Message) = {
    val s = msg.msg
    val sp = s.indexOf(' ')
    if (sp == -1) {
      None
    }
    Some(s.substring(0, sp))
  }

  def processMessage(hdr: MessageHeader, msg: Message) {
    val prefix = locatePrefix(msg)
    if (prefix eq None) return
    for (plugin <- plugins) {
      if (prefix eq plugin.prefix) {
        plugin.process(hdr.mjid.nickname, msg) match {
          case Some(s) => hdr.confProcessor ! s
          case _ => return
        }
      }
    }
  }

  def processAliases(msg: Message): Message = msg

  protected def receive = {
    case e: Envelope[Message] => {
      logger ! e
      processMessage(e.info, processAliases(e.msg))
    }
  }
}