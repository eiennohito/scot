package org.eiennohito.scot.epwing

import fuku.eb4j.hook.{Hook, HookAdapter}
import java.lang.String


/**
 * @author eiennohito
 * @since 07.12.11 
 */

class Article

class ArticleHook extends Hook[Article] {
  def clear() {}

  def getObject = null

  def isMoreInput = false

  def append(ch: Char) {}

  def append(str: String) {}

  def append(code: Int) {}

  def beginNarrow() {}

  def endNarrow() {}

  def beginUnicode() {}

  def endUnicode() {}

  def beginSubscript() {}

  def endSubscript() {}

  def beginSuperscript() {}

  def endSuperscript() {}

  def setIndent(indent: Int) {}

  def newLine() {}

  def beginNoNewLine() {}

  def endNoNewLine() {}

  def beginEmphasis() {}

  def endEmphasis() {}

  def beginDecoration(`type`: Int) {}

  def endDecoration() {}

  def beginCandidate() {}

  def endCandidateGroup(pos: Long) {}

  def endCandidateLeaf() {}

  def beginReference() {}

  def endReference(pos: Long) {}

  def beginKeyword() {}

  def endKeyword() {}

  def beginMonoGraphic(width: Int, height: Int) {}

  def endMonoGraphic(pos: Long) {}

  def beginInlineColorGraphic(format: Int, pos: Long) {}

  def endInlineColorGraphic() {}

  def beginColorGraphic(format: Int, pos: Long) {}

  def endColorGraphic() {}

  def beginSound(format: Int, start: Long, end: Long) {}

  def endSound() {}

  def beginMovie(format: Int, width: Int, height: Int, filename: String) {}

  def endMovie() {}

  def beginGraphicReference(pos: Long) {}

  def endGraphicReference() {}

  def setGraphicReference(pos: Long) {}

  def beginImagePage() {}

  def endImagePage() {}

  def beginClickableArea(x: Int, y: Int, w: Int, h: Int, pos: Long) {}

  def endClickableArea() {}

  def beginEBXACGaiji() {}

  def endEBXACGaiji() {}
}