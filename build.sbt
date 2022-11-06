ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "TP-IA"
  )

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.10"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"

libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.4"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.13.4.2"
