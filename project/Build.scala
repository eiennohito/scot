import sbt._
import Keys._

object ScotDependencies {
  val commonsLang = "comons-lang" % "commons-lang" % "2.5"  
  val barkety = "us.troutwine" %% "barkety" % "3.2.0"
  val scalaz = "org.scalaz" %% "scalaz-core" % "6.0.3"
  val scalatest = "org.scalatest" %% "scalatest" % "1.6.1" % "test"
  
  val io = Seq("core", "file") map { "com.github.scala-incubator.io" %% "scala-io-%s".format(_) % "0.2.0" }  
  
  val lift = {
	  val liftVersion = "2.4-M4" // Put the current/latest lift version here
	  Seq(
		"net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
		"net.liftweb" %% "lift-mapper" % liftVersion % "compile->default",
		"net.liftweb" %% "lift-wizard" % liftVersion % "compile->default",
		"net.liftweb" %% "lift-mongodb-record" % liftVersion,
		"javax.servlet" % "servlet-api" % "2.5" % "provided->default")
	}

  val rogue = "com.foursquare" %% "rogue" % "1.0.28" intransitive()
  
}

object Settings {

  val ourScalaVer = "2.9.1"

  val buildOrganization = "org.eiennohito"

  val buildSettings = Defaults.defaultSettings ++ Seq (
    organization := buildOrganization,
    scalaVersion := ourScalaVer
  )

}

object ScotBuild extends Build {

  import Settings.buildSettings
  import ScotDependencies._

  lazy val root = Project(
    id = "scot",
    settings = buildSettings,
    base = file(".")) aggregate (core)
  
  lazy val core = Project(
    id = "scot-core",
    settings = buildSettings,
    base = file("core")
  )
}