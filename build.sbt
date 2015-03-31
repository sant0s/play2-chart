name := """chart"""

version := "0.1.0"

organization := "name.josesantos"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
  "org.jfree" % "jfreechart" % "1.0.19"
)
