package org.eiennohito.scot.epwing.simple.gaiji

import util.parsing.combinator.RegexParsers

/**
 * @author eiennohito
 * @since 09.12.11 
 */

abstract class Literal {
  def process(g: GaijiDict): String
}

case class StringLiteral(s: String) extends Literal {
  def process(g: GaijiDict) = s
}

case class GaijiLiteral(v: String) extends Literal {
  def process(g: GaijiDict) = {
    g(v) match {
      case Some(va) => va
      case None => "_"
    }    
  }
}

object GaijiParser extends RegexParsers {

  def word = "[^\\[]+".r <~ guard("[GAIJI=".?) ^^ { StringLiteral(_) }
  
  def gaiji = "[GAIJI=" ~> ".[0-9a-fA-F]+".r <~ "]" ^^ { GaijiLiteral(_) }

  def article = word ~ (gaiji ~ word).* ^^ {
    case w ~ l => w :: l.flatMap {
      case gaiji ~ word => gaiji :: word :: Nil
    }
  }
  
  def parse(s: String) = parseAll(article, s)
}