package org.eiennohito.scot.utils

import com.weiglewilczek.slf4s.Logging

/**
 * @author eiennohito
 * @since 07.12.11 
 */

object Snippets extends Logging {
  

  def noneOnEx[A](pf: => A): Option[A] = {
    try {
      Some(pf)
    } catch {
      case e : Throwable => logger.error("caught an error", e); None
    }
  }
  
  def noneOnExOpt[A](pf: => Option[A]): Option[A] = {
    try {
      pf
    } catch {
      case e : Throwable => logger.error("caught an error", e); None
    }
  }

}