name := "akka-intro-project"

version := "0.1"

scalaVersion := "2.13.4"

val AkkaVersion =   "2.6.10"


lazy val logbackVersion = "1.2.3"
lazy val scalaTestVersion = "3.1.0"



libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test,
//Logback
"com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
"ch.qos.logback" % "logback-classic" % logbackVersion,
)
