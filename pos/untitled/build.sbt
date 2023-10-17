import Dependencies.scalaTest

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "pos",
    libraryDependencies  ++= Seq(scalaTest),
    idePackagePrefix := Some("com.pos")
  )
