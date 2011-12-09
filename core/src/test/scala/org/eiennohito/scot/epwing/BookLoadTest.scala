package org.eiennohito.scot.epwing

import fuku.eb4j.hook.DefaultHook
import fuku.eb4j.{SubBook, Book}

/**
 * @author eiennohito
 * @since 06.12.11 
 */

class BookLoadTest extends org.scalatest.FunSuite with org.scalatest.matchers.ShouldMatchers {
  test("test loading book") {
    val book = new Book("""i:\Program Files (x86)\EBWin\dics\Kokugo\Meikyou\""")
    val sb: SubBook = book.getSubBook(0)
    val c = sb.searchWord("馬鹿").getNextResult
    val txt = c.getText(new DefaultHook(sb))
    val i = 2
  }
}