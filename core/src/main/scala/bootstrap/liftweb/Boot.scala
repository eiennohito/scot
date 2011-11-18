package bootstrap.liftweb

/**
 * @author eiennohito
 * @since 07.10.11 
 */

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._
import org.eiennohito.scot.db.DbInitializer
import org.eiennohito.scot.services.InitializationService

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot extends Bootable {
  def boot {

    DbInitializer.init()

    // where to search snippet
    LiftRules.addToPackages("org.eiennohito.scot.web")

    // Build SiteMap
    def sitemap = SiteMap(
      Menu.i("Home") / "index",


      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu.i("static") / "static" / * >> Hidden,
      Menu.i("initializer") / "initialize" / net.liftweb.sitemap.* >> Hidden
    )

    //def sitemapMutators = User.sitemapMutator

    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMapFunc(() => sitemap)

    // Use jQuery 1.4
    LiftRules.jsArtifacts = net.liftweb.http.js.jquery.JQuery14Artifacts

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    //LiftRules.dispatch.append(InitializationService.check)


    // What is the function to test if a user is logged in?
    //LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    // Make a transaction span the whole HTTP request
    //S.addAround(DB.buildLoanWrapper)
  }
}
