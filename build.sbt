organization := "com.algocrafts"

name := "jmx"

version := "0.2-SNAPSHOT"

scalaVersion := "2.11.5"

scalacOptions ++= Seq("-feature", "-language:postfixOps")

resolvers += "SonaType" at "https://oss.sonatype.org/content/groups/public"

resolvers += Resolver.sonatypeRepo("releases")

resolvers += Resolver.sonatypeRepo("snapshots")

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

val akkaVersion = "2.3.9"

val libraryVersion = "1.0.1"   // or "1.1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.typesafe.akka"           %% "akka-actor"       % akkaVersion,
  "com.chuusai"                 %% "shapeless"        % "2.0.0",
  "com.github.julien-truffaut"  %% "monocle-core"     % libraryVersion,
  "com.github.julien-truffaut"  %% "monocle-generic"  % libraryVersion,
  "com.github.julien-truffaut"  %% "monocle-macro"    % libraryVersion,
  "org.scalaz"                  %% "scalaz-core"      % "7.1.0",
  "com.google.code.gson"        %  "gson"             % "2.3.1",
  "org.apache.httpcomponents"   %  "httpclient"       % "4.3.6",
  "io.spray"                    %% "spray-can"        % "1.3.2",
  "io.spray"                    %% "spray-client"     % "1.3.2",
  "io.spray"                    %% "spray-http"       % "1.3.2",
  "io.spray"                    %% "spray-httpx"      % "1.3.2",
  "io.spray"                    %% "spray-util"       % "1.3.2",
  "io.spray"                    %% "spray-io"         % "1.3.2",
  "com.typesafe.play"           %% "play-json"        % "2.3.4",
  "com.github.julien-truffaut"  %% "monocle-law"      % libraryVersion % Test,
  "com.typesafe.akka"           %% "akka-testkit"     % akkaVersion    % Test,
  "org.slf4j"                   %  "slf4j-simple"     % "1.7.7"        % Test,
  "org.scalatest"               %% "scalatest"        % "2.2.1"        % Test
)

mainClass := Some("jmx.Main")


