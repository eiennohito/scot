package org.eiennohito.scot.services

import akka.stm.TransactionFactory

/**
 * @author eiennohito
 * @since 24.11.11 
 */

trait StmManager {
  val readOnly = TransactionFactory(familyName = "readonly", readonly = true)

}

object StmManager extends StmManager