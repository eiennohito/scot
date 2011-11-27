package org.eiennohito.scot.mongo

import org.scalatest.BeforeAndAfter
import org.eiennohito.scot.db.DbInitializer

/**
 * @author eiennohito
 * @since 27.11.11 
 */

trait TestDb { self: BeforeAndAfter =>
  before {
    DbInitializer.init()
  }
}