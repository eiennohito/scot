package org.eiennohito.scot.bot.actors

import org.eiennohito.scot.messages.Message
import org.eiennohito.scot.bot.message.{MessageHeader, Envelope}
import akka.actor.{ActorRef, Actor}
import org.eiennohito.scot.plugins.SimplePlugin
import org.eiennohito.scot.bot.RegisterPlugin
import akka.config.Supervision.OneForOneStrategy

/**
 * @author eiennohito
 * @since 27.11.11 
 */

class MainRouter(logger: ActorRef) extends Actor {
  self.faultHandler = new OneForOneStrategy(classOf[Exception] :: Nil)
  self.link(logger)
  var plugins = List[SimplePlugin]()

  def locatePrefix(msg: Message) = {
    val s = msg.msg
    val sp = s.indexOf(' ')
    if (sp == -1) {
      None
    } else {
      Some(s.substring(0, sp))
    }
  }

  def processMessage(hdr: MessageHeader, msg: Message) {
    val prefix = locatePrefix(msg)
    if (prefix.isEmpty) return
    for (plugin <- plugins) {
      val pp = plugin.prefix
      if (prefix.get == pp) {
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
    case RegisterPlugin(p) => plugins = p :: plugins
  }

  override def postStop() {
    logger.stop()
  }
}