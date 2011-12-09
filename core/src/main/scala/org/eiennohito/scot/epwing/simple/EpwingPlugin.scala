package org.eiennohito.scot.epwing.simple

import org.eiennohito.scot.plugins.SimplePlugin
import org.eiennohito.scot.messages.Message
import java.lang.String
import fuku.eb4j.hook.DefaultHook

/**
 * @author eiennohito
 * @since 07.12.11 
 */

class EpwingPlugin extends SimplePlugin {

  val searcher = new EpwingSearcher(
    List(
      EpwingDict.createRecord.name("meikyo").path("""i:\Program Files (x86)\EBWin\dics\Kokugo\Meikyou\""")
    )
  )

  def name = "Epwing Plugin"

  def prefix = "epwing"

  def split(message: Message) = {
    val s = message.msg
    s.split(" ", 3).toList
  }

  def formatAnswer(iter: EpwingResultIterator): Option[String] = {
    val results = iter.take(5).toList
    results match {
      case Nil => Some("Nothing found")
      case x => {
        val s = x.map(_.getText(new DefaultHook(iter.subbook)))
          .map(searcher.degaijify(_, iter.subbook))
          .reduce(_ + " \n" +_)
        Some(s)
      }
    }
  }

  def lookup(dic: String, query: String): Option[String] = {
    import org.eiennohito.scot.utils.Snippets._
    noneOnExOpt { searcher.findIn(dic, query) } match {
      case None => Some("some kind of error, sorry pal")
      case Some(x) => formatAnswer(x)
    }
  }

  def process(sender: String, msg: Message) = {
    split(msg) match {
      case "epwing" :: dic :: query :: Nil => lookup(dic, query)
      case "epwing" :: _ => Some("invalid query")
      case _ => None
    }
  }
}