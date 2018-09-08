package $package$.service

import $package$.$service_name$.build.BuildInfo
import $package$.$service_name$.service._
import scala.concurrent.Future

class AppService() extends $service_class_name$Grpc.$service_class_name$ {

  import org.slf4j.LoggerFactory

  private val logger = LoggerFactory.getLogger(this.getClass)

  override def getAboutInfo(request: EmptyMessage): Future[AboutInfoResponse] = {
    val response = new AboutInfoResponse(BuildInfo.name, BuildInfo.version)
    Future.successful(response)
  }

  /**
    * Returns the health of this service.
    */
  override def checkHealth(request: EmptyMessage): Future[HealthCheckResponse] = {
    Future.successful(new HealthCheckResponse(HealthCheckResponse.ServingStatus.SERVING))
  }
}
