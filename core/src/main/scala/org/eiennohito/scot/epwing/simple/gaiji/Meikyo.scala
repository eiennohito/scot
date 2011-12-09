package org.eiennohito.scot.epwing.simple.gaiji

import fuku.eb4j.SubBook

/**
 * @author eiennohito
 * @since 07.12.11 
 */

trait GaijiDict {
  def apply(point: String): Option[String]

  def process(str: String, book: SubBook) : String = {
    GaijiParser.parse(str).get
      .foldLeft(new StringBuilder()) {(sb, v) => sb.append(v.process(this))}.toString()
  }
}

class StringTokenizer {

}

trait HashmapBackedGaijiDict extends GaijiDict {
  private val map = new collection.mutable.HashMap[String, String]
  def apply(point: String) = map.get(point)
  
  protected def add(p: (String, String)) = map += p
}

object Meikyo extends HashmapBackedGaijiDict {
  add("wB03E" -> "〘")
  add("wB03F" -> "〙")
  //add(0x24 -> "⓪")
}