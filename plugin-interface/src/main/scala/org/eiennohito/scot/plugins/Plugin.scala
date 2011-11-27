package org.eiennohito.scot.plugins

import org.eiennohito.scot.messages.Message

/**
 * @author eiennohito
 * @since 24.11.11 
 */

trait Plugin {
  def onStart() {}

  def onShutdown() {}

  def name : String

  def prefix : String
  
  def process(sender: String, msg: Message): Option[String]
}