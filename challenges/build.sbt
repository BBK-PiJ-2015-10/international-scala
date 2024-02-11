ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

val zioVersion= "2.0.21"

lazy val scalaTest = "org.scalatest" %% "scalatest-funsuite" % "3.2.17" % Test

lazy val root = (project in file("."))
  .settings(
    name := "challenges",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-test" % zioVersion % Test,
      scalaTest,
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
      "ch.qos.logback" % "logback-classic" % "1.3.5"
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
