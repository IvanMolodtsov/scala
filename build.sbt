ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organizationName := "com.vanmo"
ThisBuild / scalaVersion := "3.1.3"

lazy val commonSettings = Seq(
  scalaVersion := "3.1.3",
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation"
  )
)
lazy val ioc = (project in file("ioc"))
  .settings(commonSettings)
  .dependsOn(common)

lazy val common = (project in file("common"))
  .settings(commonSettings)

lazy val root = (project in file("."))
  .aggregate(ioc, common)
