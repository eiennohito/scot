package org.eiennohito.scot.db

import net.liftweb.mongodb.MongoDB
import com.mongodb.{ServerAddress, Mongo}
import net.liftweb.util.Props

/**
 * @author eiennohito
 * @since 17.11.11 
 */

object DbInit {
  def init() {
    val sa = new ServerAddress(Props.get("db.server").get, Props.getInt("db.port", ServerAddress.defaultPort()))
    MongoDB.defineDb(DbId, new Mongo(sa), Props.get("db.name").get)
  }
}