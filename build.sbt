import Dependencies._

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version      := "0.1.0"
ThisBuild / organization := "com.funcit"

lazy val root = (project in file("."))
  .settings(
    name := "zio-email",
    libraryDependencies ++= dependencies
  )

scalacOptions += "-Ypartial-unification"
