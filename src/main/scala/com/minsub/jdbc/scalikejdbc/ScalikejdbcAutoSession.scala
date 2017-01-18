package com.minsub.jdbc.scalikejdbc

import scalikejdbc._

object ScalikejdbcAutoSession extends App {
  Class.forName("com.mysql.jdbc.Driver")
  ConnectionPool.singleton(JDBCInfo.URL, JDBCInfo.ID, JDBCInfo.PW)

  // simple auto session
  implicit val session: DBSession = AutoSession
  val id = SQL("SELECT id FROM user WHERE age=?").bind(20).map(rs => rs.string(1)).single.apply()
  println("id: " + id.get)

  // auto session by method
  println("id: " + find(20).get)
  // AutoSession: readOnly -> executeQuery(), autoCommit -> execute()
  def find(age: Int)(implicit session: DBSession = AutoSession): Option[String] = {
    SQL("SELECT id FROM user WHERE age=?").bind(age).map(rs => rs.string(1)).single.apply()
  }


}
