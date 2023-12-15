ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "shop"
  )

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest-funsuite" % "3.2.17" % Test
)


