import sbt._
object Dependencies {

  val zioVersion ="2.0.19"

  lazy val zio =
    "dev.zio" %% "zio" % zioVersion

  lazy val zioTest =
    "dev.zio" %% "zio-test" % zioVersion

  lazy val zioJson =
    "dev.zio" %% "zio-json" % "0.6.2"

  lazy val zioHttp = "dev.zio" %% "zio-http" % "3.0.0-RC2"

  lazy val scalaTest = "org.scalatest" %% "scalatest-funsuite" % "3.2.17" % Test



}
