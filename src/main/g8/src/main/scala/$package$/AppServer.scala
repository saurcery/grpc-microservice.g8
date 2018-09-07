package $package$.service

object AppServer extends App {
import scala.concurrent.ExecutionContext.Implicits.global

  import com.typesafe.config.ConfigFactory

  val conf = ConfigFactory.load
  val grpcPort = conf.getString("server.grpcPort").stripPrefix(":").toInt
  val httpPort = conf.getString("server.httpPort").stripPrefix(":").toInt

  // create the app server and add the target service.
  val grpcServer = new AppGrpcServer(
    ServerBuilder
      .forPort(grpcPort)
      .addService(
        AdsBizGrpc.bindService(
          new AppService(),
          scala.concurrent.ExecutionContext.global
        )
      )
      .build()
  )
  // create an instance of the rest gateway and bind it to the grpc port.
  val gateway = new AppRestGateway(httpPort, "localhost", grpcPort)

  // Apply migration on startup
   val dbConfig = conf.getConfig("db.dataSource")
   val migration = new Migrate()
   migration.init("postgres", dbConfig)
   migration.migrate()

  // begin server init
  grpcServer.start()
  gateway.start()

  // add shutdown hooks
  grpcServer.blockUntilShutdown()
  gateway.blockUntilShutdown()
}
