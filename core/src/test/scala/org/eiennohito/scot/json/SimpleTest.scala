package org.eiennohito.yaih.json

import net.liftweb.json.{DefaultFormats, Serialization}
import net.liftweb.mongodb.{JsonObjectMeta, JsonObject}


/**
 * @author eiennohito
 * @since 17.11.11 
 */

case class Visit (name: String,  age: Int) extends JsonObject[Visit] {
  def meta = Visit
}

object Visit extends JsonObjectMeta[Visit]

class SimpleTest extends org.scalatest.FunSuite with org.scalatest.matchers.ShouldMatchers {
  test("should be jsoned") {
    implicit val format = DefaultFormats
    import net.liftweb.json.Serialization.write
    val v = new Visit("asdf", 5)
    System.out.println(write(v))
  }
}