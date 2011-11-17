package org.eiennohito.scot.mongo

import org.scalatest.BeforeAndAfter
import net.liftweb.mongodb.{MongoAddress, MongoDB, MongoIdentifier}
import org.bson.types.ObjectId
import net.liftweb.mongodb.record.{MongoMetaRecord, MongoRecord}
import net.liftweb.record.field._
import com.mongodb._
import net.liftweb.mongodb.record.field.{LongPk, ObjectIdPk}

/**
 * @author eiennohito
 * @since 17.11.11 
 */

object DbForTests extends MongoIdentifier {
  def jndiName = "forTests"
}

object DbInit {
  var initd = false

  def init {
    if (!initd) {
      MongoDB.defineDb(
        DbForTests,
          new Mongo(
            new ServerAddress("localhost"),
            new MongoOptions),
        "forTest")
      initd = true
    }

  }
}

case class SimpleTest (_id: ObjectId, name: String)

class SimpleRecord private() extends MongoRecord[SimpleRecord] with LongPk[SimpleRecord] {
  def meta = SimpleRecord

  object name extends StringField(this, 50)
  object age extends IntField(this)
}

object SimpleRecord extends SimpleRecord with MongoMetaRecord[SimpleRecord] {
  override def collectionName = "simplerecords"

  override def mongoIdentifier = DbForTests
}

class MongoTest extends org.scalatest.FunSuite with org.scalatest.matchers.ShouldMatchers with BeforeAndAfter {

  before {
    DbInit.init
  }

  test("it saves!") {
    MongoDB.use(DbForTests) { db =>
      val doc = new BasicDBObject
      doc.put("name", "MongoDb")
      val col = db.getCollection("first")
      col.save(doc)
    }
  }

  test("with record") {
    val v = SimpleRecord.createRecord.name("first").age(5)
    System.out.println(v.asJsExp)
    v.save
  }
}