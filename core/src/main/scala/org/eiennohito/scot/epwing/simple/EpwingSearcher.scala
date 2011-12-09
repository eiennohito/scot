package org.eiennohito.scot.epwing.simple

import gaiji.{Meikyo, GaijiDict}
import org.eiennohito.scot.utils.Snippets._
import collection.immutable
import fuku.eb4j.{SubBook, Result, Searcher, Book}
import java.lang.String


/**
 * @author eiennohito
 * @since 07.12.11 
 */

class EpwingResultIterator(s: Searcher, val subbook: SubBook) extends BufferedIterator[Result] {
  var nextR = s.getNextResult

  def hasNext = nextR != null

  def next() = {
    val r = nextR
    nextR = s.getNextResult
    r
  }

  def head = nextR
}

class EpwingSearcher(dicts: List[EpwingDict]) {
  
  def degaijify(string: String, book: SubBook): String = {
    degaijifiers.get(book.getName) match {
      case None => string
      case Some(dg) => dg.process(string, book)
    }
  }


  def load(dict: EpwingDict): Option[Book] = {
    noneOnEx {
      new Book(dict.path.is)
    }
  }

  val books = dicts.map(load(_)).filterNot(_.isEmpty).map(_.get)
  
  val bookDict = {
    val dic = new immutable.TreeMap[String, SubBook]()
    books.foldLeft(dic){(d, b) => d + (b.getSubBook(0).getName -> b.getSubBook(0)) }
  }

  val degaijifiers = {
    val dict = new immutable.TreeMap[String, GaijiDict]()
    dict + ("MEIKYOJJ" -> Meikyo)
  }

  def findIn(dict: String, word: String) : Option[EpwingResultIterator] = {
    val sub = bookDict(dict)
    Some(new EpwingResultIterator(sub.searchWord(word), sub))
  }
}