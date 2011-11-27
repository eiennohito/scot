package org.eiennohito.scot.services

import org.eiennohito.scot.model.ConferenceEntry
import org.eiennohito.scot.info.ConferenceInfo
import net.liftweb.mongodb.BsonDSL._

/**
 * @author eiennohito
 * @since 27.11.11 
 */

object ConfigurationService {
  
  def loadConferenceConfig(ci: ConferenceInfo): ConferenceEntry = {
    ConferenceEntry.find(("conferenceName" -> ci.room) ~ ("conferenceServer" -> ci.server)).openTheBox
  }

}