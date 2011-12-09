package org.eiennohito.scot.epwing.simple

import net.liftweb.mongodb.record.field.LongPk
import net.liftweb.mongodb.record.{MongoMetaRecord, MongoRecord}
import org.eiennohito.scot.db.NamedDatabase
import net.liftweb.record.field.StringField

/**
 * @author eiennohito
 * @since 07.12.11 
 */

class EpwingDict private() extends MongoRecord[EpwingDict] with LongPk[EpwingDict] {
  def meta = EpwingDict
  
  object path extends StringField(this, 512)
  object name extends StringField(this, 20)
}

object EpwingDict extends EpwingDict with MongoMetaRecord[EpwingDict] with NamedDatabase