package com.minsub.jdbc.scalikejdbc

import scalikejdbc._

object ScalikejdbcConnectionPool extends App {
  val settings = ConnectionPoolSettings(
    initialSize = 5,
    maxSize = 20,
    connectionTimeoutMillis = 3000L,
    validationQuery = "SELECT 1"
  )

  Class.forName("com.mysql.jdbc.Driver")
  ConnectionPool.singleton(JDBCInfo.URL, JDBCInfo.ID, JDBCInfo.PW, settings)  // Apache Commons DBCP by default.

  // borrow from pool
  using(DB(ConnectionPool.borrow())) { db =>
    val id: Option[String] = db readOnly { implicit session =>
      SQL("SELECT id FROM user LIMIT 1").map(rs => rs.string("id")).single.apply()
    }
    println("id: " + id.get)
  }

  // simpler
  val id: Option[String] = DB readOnly { implicit session =>
    SQL("SELECT id FROM user LIMIT 1").map(rs => rs.string("id")).single.apply()
  }
  println("id: " + id.get)
}
