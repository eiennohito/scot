package org.eiennohito.scot.parsing

import util.parsing.combinator.RegexParsers
import util.parsing.combinator.syntactical.{StdTokenParsers, StandardTokenParsers}

/**
 * @author eiennohito
 * @since 09.12.11 
 */

sealed abstract class Expr {
  def calc: Double
}

case class EVal(v: Double) extends Expr {
  def calc = v
}

case class EAdd(left: Expr, right: Expr) extends Expr {
  def calc = left.calc + right.calc
}

object ExprParser extends RegexParsers {

  val numRE = """[0-9]+""".r
  
  def num = numRE ^^ {
    case x => EVal(x.toDouble)
  }
  
  def expr = num ~ "+" ~ num ^^ {
    case n1 ~ "+" ~ n2 => EAdd(n1, n2)
  }
}

class CombinatorTest extends org.scalatest.FunSuite with org.scalatest.matchers.ShouldMatchers {

  test("parsessmt") {
    val x = ExprParser.parseAll(ExprParser.expr, "5+6")
    println(x.get.calc)
  }

}