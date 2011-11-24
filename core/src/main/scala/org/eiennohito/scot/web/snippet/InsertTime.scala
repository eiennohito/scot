package org.eiennohito.scot.web.snippet

import xml.NodeSeq
import java.util.Calendar

import net.liftweb._
import util.Helpers._
import org.eiennohito.scot.model.UserInfo

/**
 * @author eiennohito
 * @since 18.11.11 
 */

object InsertTime {
  def currentTime = { "* *" #> Calendar.getInstance().toString }
}