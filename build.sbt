organization := "com.algocrafts"

name := "jmx"

version := "0.2-SNAPSHOT"

scalaVersion := "2.11.4"

scalacOptions ++= Seq("-feature", "-language:postfixOps")

resolvers += "SonaType" at "https://oss.sonatype.org/content/groups/public"

resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.sonatypeRepo("snapshots")

val akkaVersion = "2.3.6"

val libraryVersion = "1.0.1"   // or "1.1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.typesafe.akka"           %% "akka-actor"      % akkaVersion,
  "com.chuusai"                 %% "shapeless"       % "2.0.0",
  "com.github.julien-truffaut"  %% "monocle-core"    % libraryVersion,
  "com.github.julien-truffaut"  %% "monocle-generic" % libraryVersion,
  "com.github.julien-truffaut"  %% "monocle-macro"   % libraryVersion,
  "com.github.julien-truffaut"  %% "monocle-law"     % libraryVersion % Test,
  "com.typesafe.akka"           %% "akka-testkit"    % akkaVersion    % Test,
  "org.slf4j"                   %  "slf4j-simple"    % "1.7.7"        % Test,
  "org.scalatest"               %% "scalatest"       % "2.2.1"        % Test
)

mainClass := Some("jmx.Main")


