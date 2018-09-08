package $package$.$service_name$.db

import io.getquill._

class $service_class_name$DAO() {
  // read the database application config
  private val ctx = new PostgresJdbcContext(SnakeCase, "db")

  import ctx._

  def testConnection(): Boolean = {
    true
  }

}
