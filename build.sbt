name := """homelist"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

javaOptions += "-Djavax.net.ssl.trustStore=resources/my-truststore.jks"
scalacOptions += "-Djavax.net.ssl.trustStore=resources/my-truststore.jks"

scalacOptions in Test += "-Djavax.net.ssl.trustStore=resources/my-truststore.jks"
javaOptions in Test ++= Seq("-Djavax.net.ssl.trustStore=resources/my-truststore.jks")

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "1.2.0"



