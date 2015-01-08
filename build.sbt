name := """game-on"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaEbean,
  "com.h2database" % "h2" % "1.3.176",
  "commons-lang" % "commons-lang" % "2.6",
  "commons-collections" % "commons-collections" % "3.2.1",
  "mysql" % "mysql-connector-java" % "5.1.32"
)
