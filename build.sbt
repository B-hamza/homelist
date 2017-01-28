name := """homelist"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

javaOptions in Test += "-Djavax.net.ssl.trustStore=resources/trust-leboncoin.jks"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "1.2.0"



