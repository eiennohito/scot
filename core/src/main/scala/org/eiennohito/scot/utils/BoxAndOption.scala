package org.eiennohito.scot.utils

import net.liftweb.common.{Empty, Full, Box}


/**
 * @author eiennohito
 * @since 26.11.11 
 */

object BoxAndOption {
  implicit def boxToOption[T](box: Box[T]) : Option[T] = {
    box match {
      case Empty => None
      case Full(x) => Some(x)
      case _ => None
    }    
  }
  
  implicit def optionToBox[T](opt: Option[T]) : Box[T] = {
    opt match {
      case None => Empty
      case Some(x) => Full(x)
    }
  }
}