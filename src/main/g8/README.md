# $service_name$



## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.<br/>
See deployment for notes on how to deploy the project on a live system.

### Prerequisites

What things you need to install the software and how to install them

```
scala 2.12
jre 1.8
sbt 1.1.6
docker
```

### Installing

Build all the project dependencies
```
sbt compile
```

Run the AppServer

```
sbt run
```

### Coding Styles
scalastyle-config.xml

## Running the tests

### Check the service health

```
curl -v http://localhost:8080/_status
```

## Deployment

```
make docker
make deploy
```

## Migration
We use Flyway Java library as the migraiton tool. [Flyway](https://flywaydb.org/getstarted/)

To add migration files:
1. place your `.sql` file in the directory `src/main/resources/db/migration`
2. Add `V__` prefix to your file 
Flyway will automatically pick up migration files from the directory and apply them at startup.


## Built With

* [GRPC](https://grpc.io/) - A high performance, open-source universal RPC framework
* [Netty](http://netty.io/) - Underlying NIO client server framework
* [ScalaPB](https://scalapb.github.io/) - Protocol Buffer Compiler for Scala
* [Quill](https://getquill.io/) - Compile-time Query generator for Scala
* [HikariCP](http://brettwooldridge.github.io/HikariCP/) - High performance JDBC connection pooling lib 
* [Logback](https://logback.qos.ch/) - SLF4J compatible logger 
* [sbt-docker](https://github.com/marcuslonnberg/sbt-docker) - sbt plugin for dockerfile generation
* [sbt-native-packager](https://github.com/sbt/sbt-native-packager) - JAR native packager for JVM based Apps
* [OpenJDK](http://openjdk.java.net/projects/jdk8u/) - JRE 8 runtime
* [Alpine JRE](https://github.com/docker-library/openjdk/blob/master/8/jre/alpine/Dockerfile) - Docker image for Alpine compatible JRE

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

Current about/version of the build is available at
```
curl -v http://localhost:8080/_about
```
We use [sbt-dynver](https://github.com/dwijnand/sbt-dynver) & [sbt-buildinfo](https://github.com/sbt/sbt-buildinfo) plugins to load this info dynamically.

Example output
```
{
  "name": "$service_name$",
  "version": "7c58839f-20180831-1535"
}
```
version here is read as = `"git describe dirty suffix" - "yyMMdd" - "time of the day"`


## Authors
"$author$"
