package $package$.service

import $package$.build.BuildInfo
import net.logstash.logback.marker.Markers.append
import net.logstash.logback.marker.Markers.appendEntries

import scala.collection.JavaConverters._

import scala.collection.immutable
import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

class AppService() extends AdsBizGrpc.AdsBiz {

  import org.slf4j.LoggerFactory

  private val logger = LoggerFactory.getLogger(this.getClass)
  //val adsBizDAO = new AdsBizDAO()

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
