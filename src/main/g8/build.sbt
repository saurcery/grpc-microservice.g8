
////////// Project Build Info //////////
// Note : "version" string is auto set by sbt-dynver plugin, below we format the version.

def versionFmt(out: sbtdynver.GitDescribeOutput): String = {
  val dirtySuffix = out.dirtySuffix.dropPlus.mkString("-", "")
  if (out.isCleanAfterTag) out.ref.dropV.value + dirtySuffix // no commit info if clean after tag
  else out.ref.dropV.value + out.commitSuffix.mkString("-", "-", "") + dirtySuffix
}

def fallbackVersion(d: java.util.Date): String = s"HEAD-\${sbtdynver.DynVer timestamp d}"

inThisBuild(List(
  version := dynverGitDescribeOutput.value.mkVersion(versionFmt, fallbackVersion(dynverCurrentDate.value)),
  dynver := {
    val d = new java.util.Date
    sbtdynver.DynVer.getGitDescribeOutput(d).mkVersion(versionFmt, fallbackVersion(d))
  }
))

lazy val root = (project in file(".")).
  enablePlugins(BuildInfoPlugin).
  settings(
    name := "$service_name$",
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "$package$.build"
  )

scalacOptions in ThisBuild ++= Seq("-unchecked", "-deprecation")

////////// PROTO GENERATION //////////
PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value / "protobuf",
  grpcgateway.generators.GatewayGenerator -> (sourceManaged in Compile).value / "protobuf",
)

val grpcVersion              = "1.13.2"
val grpcGatewayVersion       = "0.0.9"
val scalapbVersion           = "0.7.0"
val quillVersion             = "2.5.4"
val pgVersion                = "9.4.1208"
val flywayVersion            = "5.1.4"
val configVersion            = "1.3.2"
val logbackVersion           = "1.2.3"
val logbackEncoderVersion    = "5.2"

resolvers += Resolver.bintrayRepo("beyondthelines", "maven")

libraryDependencies ++= Seq(
  // ScalaPB
  "com.thesamet.scalapb"  %% "scalapb-runtime"         % scalapbVersion % "protobuf",
  "com.thesamet.scalapb"  %% "scalapb-runtime-grpc"    % scalapbVersion,

  // Scala-JSON conversion
  "com.thesamet.scalapb"  %% "scalapb-json4s"          % scalapbVersion,

  // GRPC
  "io.grpc"               %  "grpc-netty"              % grpcVersion,
  "beyondthelines"        %% "grpcgatewayruntime"      % grpcGatewayVersion % "compile,protobuf",

  // Database
  "org.postgresql"        % "postgresql"               % pgVersion,
  "io.getquill"           %% "quill-jdbc"              % quillVersion,
  "io.getquill"           %% "quill-core"              % quillVersion,

  // Database Migration
  "org.flywaydb"          % "flyway-core"              % flywayVersion,

  // Configuration
  "com.typesafe"          % "config"                   % configVersion,

  // Logging
  "ch.qos.logback"        % "logback-classic"          % logbackVersion,
  "net.logstash.logback"  % "logstash-logback-encoder" % logbackEncoderVersion
)

////////// DOCKER BUILD //////////
// adopted from :: https://github.com/marcuslonnberg/sbt-docker/blob/master/examples/sbt-native-packager/build.sbt

// enable docker plugins
enablePlugins(sbtdocker.DockerPlugin, JavaAppPackaging)
// enable Almquist Shell to execute our app binary
enablePlugins(AshScriptPlugin)

dockerfile in docker := {
  val appDir: File = stage.value
  val targetDir = "/usr/local"

  new Dockerfile {
    // alpine all the way
    // note: alpine shrinks down the container size, AshScript enables us to run the app
    from("openjdk:jre-alpine")
    expose(80)
    expose(9111)
    env("JAVA_OPTS" -> "-Xmx$service_memory$")
    entryPoint(s"\$targetDir/bin/\${executableScriptName.value}")
    copy(appDir, targetDir)
  }
}
buildOptions in docker := BuildOptions(
  cache = false,
  removeIntermediateContainers = BuildOptions.Remove.Always,
  pullBaseImage = BuildOptions.Pull.Always
)
