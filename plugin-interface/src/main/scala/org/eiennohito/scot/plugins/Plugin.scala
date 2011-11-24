package org.eiennohito.scot.plugins

/**
 * @author eiennohito
 * @since 24.11.11 
 */

trait Plugin {

  def onStart() {}

  def onShutdown() {}

  def name : String

  def prefix : String
}