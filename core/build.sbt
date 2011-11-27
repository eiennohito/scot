import ScotDependencies._

seq(webSettings :_*)

name := "scot-core"

libraryDependencies ++= Seq(scalaz, scalatest) ++ lift ++ io

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "se.scalablesolutions.akka" % "akka-stm" % "1.2"

libraryDependencies +=  "org.eclipse.jetty" % "jetty-webapp" % "8.0.4.v20111024" % "container"

libraryDependencies += "com.foursquare" %% "rogue" % "1.0.28" intransitive()

libraryDependencies += "ch.qos.logback" % "logback-classic" % "0.9.28" % "runtime"

libraryDependencies += "com.weiglewilczek.slf4s" %% "slf4s" % "1.0.7"