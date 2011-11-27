package org.eiennohito.scot.bot

import akka.actor.ActorRef

/**
 * @author eiennohito
 * @since 24.11.11 
 */

case class CoreActors(
                       mainRouter: ActorRef,
                       presenceLogger: ActorRef,
                       chatSupervisor: ActorRef) {}