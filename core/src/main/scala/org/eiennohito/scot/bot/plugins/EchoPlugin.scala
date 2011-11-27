package org.eiennohito.scot.bot.plugins

import org.eiennohito.scot.plugins.Plugin
import org.eiennohito.scot.messages.Message

/**
 * @author eiennohito
 * @since 27.11.11 
 */

class EchoPlugin extends Plugin {
  def name = "Echo plugin"

  def prefix = "echo"

  def process(sender: String, msg: Message) = {
    Some("%s: %s".format(sender, msg.msg))
  }
}