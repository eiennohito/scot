package org.eiennohito.scot.services

import org.eiennohito.scot.model.ConferenceEntry
import org.eiennohito.scot.info.ConferenceInfo
import net.liftweb.mongodb.BsonDSL._
import us.troutwine.barkety.jid.MucJID

/**
 * @author eiennohito
 * @since 27.11.11 
 */

object ConfigurationService {

  def loadConferenceConfig(mjid: MucJID): ConferenceEntry =
    loadConferenceConfig(
      ConferenceInfo(mjid.room, mjid.server)
    )
  
  def loadConferenceConfig(ci: ConferenceInfo): ConferenceEntry = {
    ConferenceEntry.find(("room" -> ci.room) ~ ("server" -> ci.server)).openTheBox
  }

}