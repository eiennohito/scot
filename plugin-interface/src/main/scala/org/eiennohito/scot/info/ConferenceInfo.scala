package org.eiennohito.scot.info

/**
 * @author eiennohito
 * @since 24.11.11 
 */

case class ConferenceInfo(room: String, server: String) {}

case class ConferenceLoginInfo (
    room: String,
    server: String,
    nickname: String,
    password: Option[String]
                                 ) {
  def info = ConferenceInfo(room, server)
}