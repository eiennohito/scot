package org.eiennohito.scot.services

import org.eiennohito.scot.model.ConferenceEntry
import org.eiennohito.scot.info.ConferenceInfo
import net.liftweb.mongodb.BsonDSL._
import us.troutwine.barkety.jid.MucJID

/**
 * @author eiennohito
 * @since 27.11.11 
 */

trait Configurator {
  def loadConferenceConfig(mjid: MucJID): ConferenceEntry =
      loadConferenceConfig(
        ConferenceInfo(mjid.room, mjid.server)
      )
  def loadConferenceConfig(ci: ConferenceInfo): ConferenceEntry
}

trait MongoConfigurator extends Configurator {
  def loadConferenceConfig(ci: ConferenceInfo): ConferenceEntry = {
    ConferenceEntry.find(("room" -> ci.room) ~ ("server" -> ci.server)).openTheBox
  }
}

trait HasConfigurator {
  val configurator: Configurator
}

trait HasMongoConfigurator { self : HasConfigurator =>
  override val configurator = new MongoConfigurator {}
}