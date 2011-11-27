package org.eiennohito.scot.model

import org.eiennohito.scot.db.NamedDatabase
import net.liftweb.mongodb.record.field.{DateField, LongPk}
import net.liftweb.mongodb.record.{BsonMetaRecord, BsonRecord, MongoMetaRecord, MongoRecord}
import net.liftweb.record.field.{LongField, StringField}
import java.util.Date
import org.eiennohito.scot.services.DateService

/**
 * @author eiennohito
 * @since 17.11.11 
 */

class Message private() extends MongoRecord[Message] with LongPk[Message] {
  def meta = Message

  object content extends StringField(this, 2500)
  object nick extends StringField(this, 250)
  object time extends DateField(this)
}

object Message extends Message with MongoMetaRecord[Message] with NamedDatabase

class Participant private() extends MongoRecord[Participant] with LongPk[Participant] {
  def meta = Participant

  object conf extends LongField(this)
  object nick extends StringField(this, 250)
  object jid extends StringField(this, 250)
}

object Participant extends Participant with MongoMetaRecord[Participant] with NamedDatabase

class ChangeNickEvent private() extends MongoRecord[ChangeNickEvent] with LongPk[ChangeNickEvent] {
  def meta = ChangeNickEvent

  object who extends LongField(this, 250)
  object from extends StringField(this, 250)
  object to extends StringField(this, 250)
  object when extends DateField(this)

  def participant = Participant.find(who.is)
}

object ChangeNickEvent extends ChangeNickEvent with MongoMetaRecord[ChangeNickEvent] with NamedDatabase

class OnlineTime private() extends MongoRecord[OnlineTime] with LongPk[OnlineTime] {
  def meta = OnlineTime

  object who extends LongField(this)
  object cameOnline extends DateField(this)
  object wentOffline extends DateField(this) {
    override def optional_? = true
  }
}

object OnlineTime extends OnlineTime with MongoMetaRecord[OnlineTime] with NamedDatabase