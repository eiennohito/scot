package org.eiennohito.scot.services

import net.liftweb.http.provider.HTTPRequest
import org.eiennohito.scot.model.UserInfo
import net.liftweb.json.JsonDSL._
import net.liftweb.http.{Req, RedirectResponse, LiftRules, S}
import net.liftweb.common.{Empty, Full, Box}

/**
 * @author eiennohito
 * @since 18.11.11 
 */

object InitializationService {

  def checkIfInited(req: HTTPRequest) = {
    if (!hasAdminAccount) {
      S.redirectTo("/initialize/init")
    }
  }

  val check : LiftRules.DispatchPF = {
    case Req("initialize" :: "init" :: Nil, _, _) => () => Empty
    case Req(_, _, _) => () => {
      if (hasAdminAccount) Empty else Full(RedirectResponse("/initialize/init"))
    }
  }

  var have = false;

  def hasAdminAccount : Boolean = {
    //UserInfo where (_.admin eqs true) exists()
    if (!have) {
      have = UserInfo.count("admin" -> true) != 0
    }
    have
  }

}