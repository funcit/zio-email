import sbt._

object Dependencies {
  lazy val scalaTestVersion = "3.0.5"

  val dependencies = Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion,
    "com.sun.mail"      % "javax.mail"      % "1.6.2",
    "dev.zio"           %% "zio"            % "1.0.0-RC10-1",
    "org.typelevel"     %% "cats-core"      % "2.0.0-M1",
  )

}
