ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organizationName := "com.vanmo"
ThisBuild / scalaVersion := "3.1.3"

lazy val root = (project in file("."))
  .settings(
    name := "Scala-test",
    idePackagePrefix := Some("com.vanmo")
  )
