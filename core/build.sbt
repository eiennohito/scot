import ScotDependencies._

name := "scot-core"

libraryDependencies ++= Seq(barkety, scalaz, scalatest) ++ lift ++ io

