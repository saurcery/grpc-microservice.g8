addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.18")
addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.5.0")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.5")
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.0")
addSbtPlugin("com.dwijnand" % "sbt-dynver" % "3.0.0")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")

resolvers += Resolver.bintrayRepo("beyondthelines", "maven")

libraryDependencies ++= Seq(
  "com.thesamet.scalapb" %% "compilerplugin"          % "0.7.0",
  "beyondthelines"         %% "grpcgatewaygenerator"    % "0.0.9"
)
