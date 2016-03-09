name := "slack-lambda-chatops"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.4.1",
  "org.scalaz" %% "scalaz-core" % "7.2.1",
  "com.chuusai" %% "shapeless" % "2.3.0",
  "com.github.tototoshi" %% "play-json-naming" % "1.0.0",
  "com.github.scopt" %% "scopt" % "3.4.0",
  "com.amazonaws" % "aws-java-sdk" % "1.10.11",
  "com.amazonaws" % "aws-java-sdk-route53" % "1.10.14",
  "com.amazonaws" % "aws-java-sdk-elasticloadbalancing" % "1.10.50",
  "com.amazonaws" % "aws-lambda-java-core" % "1.1.0",
  "com.amazonaws" % "aws-lambda-java-events" % "1.1.0",
  "com.typesafe.play" %% "play-json" % "2.4.6",
  "net.gpedro.integrations.slack" % "slack-webhook" % "1.1.1",
  "org.apache.commons" % "commons-lang3" % "3.4",
  "org.scalatest" %% "scalatest" % "3.0.0-M7" % "test"
)

sources in (Compile, doc) := Seq.empty

publishArtifact in (Compile, packageDoc) := false
