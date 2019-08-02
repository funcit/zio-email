import sbt._

object Dependencies {
  lazy val scalaTestVersion = "3.0.5"
  lazy val javaxMailVersion = "1.6.2"
  lazy val zioVersion = "1.0.0-RC10-1"
  lazy val catsVersion = "2.0.0-M1"

  val dependencies = Seq(
    "org.scalatest"     %% "scalatest"      % scalaTestVersion,
    "com.sun.mail"      % "javax.mail"      % javaxMailVersion,
    "dev.zio"           %% "zio"            % zioVersion,
    "org.typelevel"     %% "cats-core"      % catsVersion
  )
}
