package com.minsub.jdbc.scalikejdbc

import java.sql.DriverManager
import scalikejdbc._

object ScalikejdbcSample extends App {
  // init JDBC driver
  Class.forName("com.mysql.jdbc.Driver")

  // Simple Connection
  using(DB(DriverManager.getConnection(JDBCInfo.URL, JDBCInfo.ID, JDBCInfo.PW))) { db =>
    val id: Option[String] = db readOnly { implicit session =>
      SQL("SELECT id, age FROM user LIMIT 1").map(rs => rs.string("id")).single.apply()
    }
    println("id: " + id.get)
  }
}
