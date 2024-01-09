
import Dependencies.*

ThisBuild / scalaVersion     := "2.13.12"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "infra",
    libraryDependencies ++= Seq(
      zio,
      zioTest,
      zioHttp,
      scalaTest
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)

dockerExposedPorts := Seq(9090)

dockerBaseImage       := "openjdk:11"

dockerUsername := sys.props.get("docker.username")
dockerRepository := sys.props.get("docker.registry")