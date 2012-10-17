organization := "codeoptimus"

name := "MyFuture"

version := "1.0"

autoScalaLibrary := false

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= {
  Seq(
    "junit" % "junit" % "4.10",
    "com.typesafe.akka" % "akka-actor" % "2.0.3"
  )
}

