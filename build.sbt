import java.util.concurrent.TimeUnit

import sbt._
import Keys._

// Variables:
val scioVersion = "0.4.7"
val slf4jVersion = "1.7.25"
val beamVersion = "2.2.0"

lazy val commonSettings = Defaults.coreDefaultSettings ++ Seq(
  organization          := "com.spotify.data.example",
  version               := "0.1.0-SNAPSHOT",
  scalaVersion          := "2.11.11",
  scalacOptions         ++= Seq("-target:jvm-1.8",
                                "-deprecation",
                                "-feature",
                                "-unchecked"),
  javacOptions          ++= Seq("-source", "1.8",
                                "-target", "1.8"),
  packJarNameConvention := "original",

  // protobuf-lite is an older subset of protobuf-java and causes issues
  excludeDependencies += "com.google.protobuf" % "protobuf-lite",

  publish := {},
  publishLocal := {},
  publishArtifact := false
)

lazy val root: Project = Project(
  "simple-s3-parent",
  file(".")
).settings(
  commonSettings
).aggregate(
  pipeline
).enablePlugins(
  PackPlugin
)

lazy val pipeline: Project = Project(
  "simple-s3",
  file("simple-s3")
).settings(
  commonSettings,
  description := "simple-s3 scio pipeline",
  libraryDependencies ++= Seq(
    "com.spotify" %% "scio-core" % scioVersion,
    "com.spotify" %% "scio-hdfs" % scioVersion,
    "org.slf4j" % "slf4j-simple" % slf4jVersion,
    "org.apache.beam" % "beam-runners-direct-java" % beamVersion,
    "org.apache.beam" % "beam-runners-google-cloud-dataflow-java" % beamVersion,
    "com.amazonaws" % "aws-java-sdk" % "1.7.4",
    "org.apache.hadoop" % "hadoop-aws" % "2.7.3",
    "com.spotify" %% "scio-test" % scioVersion % "test"
  ),
  // Required for typed BigQuery macros:
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
)

lazy val repl: Project = Project(
  "repl",
  file(".repl")
).settings(
  commonSettings,
  description := "Scio REPL for simple-s3. To start: `sbt repl/run`.",
  libraryDependencies ++= Seq(
    "com.spotify" %% "scio-repl" % scioVersion
  ),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
  mainClass in Compile := Some("com.spotify.scio.repl.ScioShell")
).dependsOn(
  pipeline
)
