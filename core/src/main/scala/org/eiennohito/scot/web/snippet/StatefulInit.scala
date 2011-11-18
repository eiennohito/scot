package org.eiennohito.scot.web.snippet

import net.liftweb.util.Helpers._
import net.liftweb.http.{S, SHtml, StatefulSnippet}
import org.eiennohito.scot.services.InitializationService
import org.eiennohito.scot.model.UserInfo


/**
 * @author eiennohito
 * @since 18.11.11 
 */

class StatefulInit extends StatefulSnippet {

  private var username = ""
  private var passwd = ""
  private var pwd_conf = ""

  def dispatch = {case "render" => render}

  def render = {
    if (InitializationService.hasAdminAccount) {
      S.redirectTo("init_result")
    }
    "name=username" #> SHtml.text(username, username = _) &
    "name=password" #> SHtml.text(passwd, passwd = _) &
    "name=confirm_pwd" #> SHtml.text(pwd_conf, pwd_conf = _) &
    "type=submit" #> SHtml.onSubmitUnit(process)
  }


  private def process = {
    () => {
      var ok = true
      if (username eq "") {
        S.error("username can't be empty")
        ok = false
      }

      if (passwd eq "") {
        S.error("password can't be empty")
        ok = false
      }

      if (passwd != pwd_conf) {
        S.error("password should equal to confirmation")
        ok = false
      }

      if (ok) {
        UserInfo.createRecord.username(username).password(passwd).admin(true).save
      }
    }
  }
}