package org.eiennohito.scot.info

/**
 * @author eiennohito
 * @since 24.11.11 
 */

case class ConferenceInfo(
                           room: String,
                           server: String,
                           nickname: Option[String],
                           password: Option[String]) {}