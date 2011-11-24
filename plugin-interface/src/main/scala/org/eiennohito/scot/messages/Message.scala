package org.eiennohito.scot.messages

import java.util.Date

/**
 * @author eiennohito
 * @since 24.11.11 
 */

class MessageInfo (val sender: Sender, val time: Date) {
  
}

case class Message(info: MessageInfo, msg: String) {

  def sender = info.sender
  def time = info.time
}
