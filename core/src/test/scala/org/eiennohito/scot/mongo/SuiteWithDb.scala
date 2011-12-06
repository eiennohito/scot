package org.eiennohito.scot.mongo

import org.eiennohito.scot.db.DbInitializer
import org.scalatest.{FunSuite, BeforeAndAfterAll}

/**
 * @author eiennohito
 * @since 27.11.11 
 */

trait SuiteWithDb extends FunSuite with BeforeAndAfterAll {
  override protected def beforeAll() {
    try {
      DbInitializer.init()
    } catch {
      case _ => //...
    }
    super.beforeAll()
  }
}