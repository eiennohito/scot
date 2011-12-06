package org.eiennohito.scot.bot

import actors.MainRouter
import muc.ConferenceListener
import net.liftweb.util.Props
import akka.actor.Actor._
import akka.actor.ActorRef
import plugins.EchoPlugin
import us.troutwine.barkety.jid.JID
import us.troutwine.barkety.{JoinRoom, ChatSupervisor}
import org.eiennohito.scot.plugins.SimplePlugin
import org.eiennohito.scot.services.MongoParticipantResolverPresent
import org.eiennohito.scot.bot.logging.{MessageLogger, PresenceLogger}
import org.eiennohito.scot.info.{ConferenceLoginInfo, ConferenceInfo}

/**
 * @author eiennohito
 * @since 17.11.11 
 */

case class RegisterPlugin(p: SimplePlugin)

object BotBootstrap {

  def chatsup = core.get.chatSupervisor
  var core : Option[CoreActors] = None
  val confs = new scala.collection.mutable.HashMap[ConferenceInfo, ActorRef]

  def loginToRoom(li: ConferenceLoginInfo) = {
    val par = actorOf (new ConferenceListener(core.get, li) with MongoParticipantResolverPresent).start
    confs += li.info -> par
    par
  }

  def registerPlugin(p: => SimplePlugin) {
    core.map { _.mainRouter ! RegisterPlugin(p) }
  }

  def registerCorePlugins() {
    registerPlugin(new EchoPlugin)
  }

  def launch() {
    val jid = JID(Props.get("bot.jid").get)
    val passwd = Props.get("bot.passwd").get
    core = Some(CoreActors(
      actorOf(new MainRouter(actorOf(
        new MessageLogger with MongoParticipantResolverPresent
      ).start)),
      actorOf(new PresenceLogger with MongoParticipantResolverPresent),
      actorOf(new ChatSupervisor(jid, passwd))
    ))
    core map {x => x.mainRouter :: x.presenceLogger :: x.chatSupervisor :: Nil} map { _.foreach(_.start()) }
    registerCorePlugins()
  }

  def stop() {
    confs foreach { _._2.stop() }
    core map {x => x.mainRouter :: x.presenceLogger :: x.chatSupervisor :: Nil} map { _.foreach(_.stop()) }
    confs.clear()
    core = None
  }
}