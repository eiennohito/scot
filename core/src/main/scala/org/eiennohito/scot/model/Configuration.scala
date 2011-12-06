package org.eiennohito.scot.model

import net.liftweb.mongodb.record.field.LongPk
import net.liftweb.mongodb.record.{MongoMetaRecord, MongoRecord}
import org.eiennohito.scot.db.NamedDatabase
import net.liftweb.record.field.{BooleanField, StringField}

/**
 * @author eiennohito
 * @since 17.11.11 
 */

class ConferenceEntry private() extends MongoRecord[ConferenceEntry] with LongPk[ConferenceEntry] {
  def meta = ConferenceEntry

  object room extends StringField(this, 250)
  object server extends StringField(this, 250)
  object usingNick extends StringField(this, 250)
  object password extends StringField(this, 250)
}

object ConferenceEntry extends ConferenceEntry with MongoMetaRecord[ConferenceEntry] with NamedDatabase {
  override def collectionName = "confentry"
}


class UserInfo private() extends MongoRecord[UserInfo] with LongPk[UserInfo] {
  def meta = UserInfo

  object username extends StringField(this, 50)
  object password extends StringField(this, 128)
  object admin extends BooleanField(this)
}

object UserInfo extends UserInfo with MongoMetaRecord[UserInfo] with NamedDatabase