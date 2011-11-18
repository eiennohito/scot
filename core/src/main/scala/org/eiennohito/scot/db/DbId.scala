package org.eiennohito.scot.db

import net.liftweb.util.Props
import net.liftweb.mongodb.{MongoMeta, MongoIdentifier}

/**
 * @author eiennohito
 * @since 17.11.11 
 */

object DbId extends MongoIdentifier {
  val dbName = Props.get("db.name", "scot_db")
  def jndiName = dbName
}

trait NamedDatabase { self: MongoMeta[_] =>
  override def mongoIdentifier = DbId
}