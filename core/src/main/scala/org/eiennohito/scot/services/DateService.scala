package org.eiennohito.scot.services

import java.util.{Date, TimeZone, Calendar}


/**
 * @author eiennohito
 * @since 27.11.11 
 */

object DateService {

  val defaultDate =  {
    val c = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    c.setTimeInMillis(0L)
    c.getTime
  }

}