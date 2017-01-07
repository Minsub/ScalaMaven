package com.minsub.jdbc.scalikejdbc

import java.sql.DriverManager

import scalikejdbc._

object ScalikejdbcMultiple extends App {
  val url_pro = "jdbc:as400://203.242.32.18;"
  val url_dev = "jdbc:as400://203.242.35.200;"
  val id = "DPBIZJMS"
  val pw = "wlalstjq2"

  DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver())
  ConnectionPool.add("pro", url_pro, id, pw)
  ConnectionPool.add("dev", url_dev, id, pw)

  val name: Option[String] = NamedDB("dev") readOnly { implicit session =>
    SQL("SELECT 'A' COL1, 'B' COL2 FROM SYSIBM.SYSDUMMY1").map(rs => rs.string("COL1")).single.apply()
  }

  println("result: " + name.get)

}
