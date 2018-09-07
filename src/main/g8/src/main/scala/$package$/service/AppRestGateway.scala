package $package$.service

import java.util.logging.Logger

import $package$.$service_name$.service.$service_class_name$Handler
import grpcgateway.server.GrpcGatewayServerBuilder
import io.grpc.ManagedChannelBuilder

import scala.concurrent.ExecutionContext
import scala.sys.ShutdownHookThread

class AppRestGateway(port: Int, grpcHost: String, grpcPort: Int)(implicit ec: ExecutionContext) {
  private val logger: Logger = Logger.getLogger(classOf[AppGrpcServer].getName)

  private val channel = ManagedChannelBuilder
    .forAddress(grpcHost, grpcPort)
    .usePlaintext()
    .build()

  private val gateway = GrpcGatewayServerBuilder
    .forPort(port)
    .addService(new $service_class_name$Handler(channel))
    .build()

  private var shutdownHook: Option[ShutdownHookThread] = None

  def start(): Unit = {
    gateway.start()
    logger.info(s"GRPC Gateway started, listening on \$port")
    shutdownHook = Option(
      sys.addShutdownHook {
        // Use stderr here since the logger may has been reset by its JVM shutdown hook.
        System.err.println("*** shutting down gRPC gateway since JVM is shutting down")
        stop()
        System.err.println("*** gRPC Gateway shut down")
      }
    )
  }

  def stop(): Unit = gateway.shutdown()

  def blockUntilShutdown(): Unit = shutdownHook.foreach(_.join())
}
