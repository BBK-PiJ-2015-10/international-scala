import Dependencies._

ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.hs.code.challenge"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "hs",
    libraryDependencies ++= Seq(
      zio,
      zioTest,
      zioHttp,
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
