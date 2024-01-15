ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "rs.com"
ThisBuild / organizationName := "example"

val zioVersion ="2.0.19"
val scalaXmlVersion = "2.2.0"

lazy val zio =
  "dev.zio" %% "zio" % zioVersion

lazy val zioTest =
  "dev.zio" %% "zio-test" % zioVersion

lazy val zioJson =
  "dev.zio" %% "zio-json" % "0.6.2"

lazy val zioHttp = "dev.zio" %% "zio-http" % "3.0.0-RC2"

lazy val scalaTest = "org.scalatest" %% "scalatest-funsuite" % "3.2.17" % Test

lazy val scalaXml= "org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion


lazy val root = (project in file("."))
  .settings(
    name := "rs",
    libraryDependencies ++= Seq(
      zio,
      zioJson,
      zioTest,
      zioHttp,
      scalaTest,
      scalaXml
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
