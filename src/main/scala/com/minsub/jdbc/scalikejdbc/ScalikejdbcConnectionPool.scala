package com.minsub.jdbc.scalikejdbc

import java.sql.DriverManager

import scalikejdbc._

object ScalikejdbcConnectionPool extends App {
  val url = "jdbc:as400://203.242.35.200;"
  val id = "DPBIZJMS"
  val pw = "wlalstjq2"

  DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver())
  ConnectionPool.singleton(url, id, pw)  // Apache Commons DBCP by default.

  using(DB(ConnectionPool.borrow())) { db =>
    val name: Option[String] = db readOnly { implicit session =>
      SQL("SELECT 'A' COL1, 'B' COL2 FROM SYSIBM.SYSDUMMY1").map(rs => rs.string("COL1")).single.apply()
    }
    println("result: " + name.get)
  }

  // simpler
  val name: Option[String] = DB readOnly { implicit session =>
    SQL("SELECT 'A' COL1, 'B' COL2 FROM SYSIBM.SYSDUMMY1").map(rs => rs.string("COL1")).single.apply()
  }

  println("result: " + name.get)

}
