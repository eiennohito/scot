package org.eiennohito.scot.resource

import scalax.io.Resource
import sys.Prop
import net.liftweb.util.Props
import java.util.Properties

/**
 * @author eiennohito
 * @since 17.11.11 
 */

class ResouceTest extends org.scalatest.FunSuite with org.scalatest.matchers.ShouldMatchers {
  test("should load resource all right") {
    val v = Props.get("help")
    v should equal ("1")
  }
}