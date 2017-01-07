package com.minsub.jdbc.scalikejdbc

import java.sql.DriverManager

import scalikejdbc._

object ScalikejdbcSample extends App {
  val url = "jdbc:as400://203.242.35.200;"
  val id = "DPBIZJMS"
  val pw = "wlalstjq2"

  DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver())

  // Simple Connection
  using(DB(DriverManager.getConnection(url, id, pw))) { db =>
    val name: Option[String] = db readOnly { implicit session =>
      SQL("SELECT 'A' COL1, 'B' COL2 FROM SYSIBM.SYSDUMMY1").map(rs => rs.string("COL1")).single.apply()
    }
    println("result: " + name.get)
  }

}
