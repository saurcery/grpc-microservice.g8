# grpc-microservice.g8

Giter template to launch a simple Scala-gRPC microservice with very powerful addons. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.<br/>

### Prerequisites

What things you need to install the software and how to install them

```
scala 2.12
jre 1.8
sbt 1.1.6
docker
```
### Usage

To create this template apply:
```
sbt new saurcery/grpc-microservice.g8
```

You will be prompted for specific optional values to derive the final service code.
These properties are stored in default.properties and quite self-explanatory.

Change directory to the newly created project from this template(name option) and run:
```
sbt run
```

To verify the server is up try:
```
curl -v http://localhost:8080/_status
```

Default http port is set to 8080.

### Development

Clone the project and get a copy locally.

Install giter8 locally. Instructions can be found at: http://www.foundweekends.org/giter8/setup.html

Compile the template locally.
```
g8 file://path-to-project/grpc-microservice.g8
```
