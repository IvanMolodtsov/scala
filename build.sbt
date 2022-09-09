ThisBuild / version := "0.1.0-SNAPSHOT"
Global / excludeLintKeys += idePackagePrefix
ThisBuild / organizationName := "com.vanmo"
ThisBuild / scalaVersion := "3.1.3"
ThisBuild / semanticdbEnabled := true
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"
ThisBuild / scalafixOnCompile := true

lazy val commonSettings = Seq(
  scalaVersion := "3.1.3",
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation"
  ),
  libraryDependencies ++= Seq(
    "org.scalatest" %%% "scalatest" % "3.2.12" % "test"
  ),
  idePackagePrefix := Some("com.vanmo")
)

lazy val ioc = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("ioc"))
  .settings(commonSettings)
  .dependsOn(common)
  .enablePlugins(ScalafixPlugin)

lazy val common = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("common"))
  .settings(commonSettings)
  .enablePlugins(ScalafixPlugin)
  .jsSettings(libraryDependencies += "org.scala-js" %%% "scala-js-macrotask-executor" % "1.0.0")

lazy val bundler = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("bundler"))
  .settings(commonSettings)
  .dependsOn(common, ioc)
  .enablePlugins(ScalafixPlugin)

lazy val root = (project in file("."))
  .aggregate(ioc.js, ioc.jvm, common.js, common.jvm)
