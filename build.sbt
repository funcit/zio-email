import Dependencies._

ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version      := "0.1.0"
ThisBuild / organization := "com.funcit"
ThisBuild / description := "Purely functional email client based on ZIO"

lazy val root = (project in file("."))
  .settings(
    name := "zio-email",
    libraryDependencies ++= dependencies
  )

scalacOptions += "-Ypartial-unification"

bintrayOrganization := Some("funcit")
bintrayRepository := "zio"
publishTo := Some("bintray" at "https://api.bintray.com/maven/funcit/zio/zio-email/;publish=1")
credentials += Credentials(Path.userHome / ".sbt" / ".credentials")
publishMavenStyle := true
publishArtifact := true
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
homepage := Some(url("https://github.com/funcit/zio-email"))
