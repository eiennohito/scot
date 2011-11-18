import ScotDependencies._

name := "scot-core"

libraryDependencies ++= Seq(scalaz, scalatest) ++ lift ++ io

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "se.scalablesolutions.akka" % "akka-stm" % "1.2"