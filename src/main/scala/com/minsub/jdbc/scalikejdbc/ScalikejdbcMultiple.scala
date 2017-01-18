package com.minsub.jdbc.scalikejdbc

import scalikejdbc._

object ScalikejdbcMultiple extends App {
  Class.forName("com.mysql.jdbc.Driver");
  ConnectionPool.add("pro", JDBCInfo.URL, JDBCInfo.ID, JDBCInfo.PW)
  ConnectionPool.add("dev", JDBCInfo.URL2, JDBCInfo.ID, JDBCInfo.PW)

  val id: Option[String] = NamedDB("dev") readOnly { implicit session =>
    SQL("SELECT id FROM user LIMIT 1").map(rs => rs.string("id")).single.apply()
  }
  println("id: " + id.get)
}
