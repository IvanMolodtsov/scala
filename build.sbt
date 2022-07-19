ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organizationName := "com.vanmo"
ThisBuild / scalaVersion := "3.1.3"

lazy val commonSettings = Seq(
  scalaVersion := "3.1.3",
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation"
  ),
  libraryDependencies += "org.scalatest" %%% "scalatest" % "3.2.12" % "test"
)

lazy val ioc = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("ioc"))
  .settings(commonSettings)
  .settings(idePackagePrefix := Some("com.vanmo.ioc"))
  .dependsOn(common)

lazy val common = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("common"))
  .settings(commonSettings)
  .settings(idePackagePrefix := Some("com.vanmo.common"))

lazy val root = (project in file("."))
  .aggregate(ioc.js, ioc.jvm, common.js, common.jvm)
