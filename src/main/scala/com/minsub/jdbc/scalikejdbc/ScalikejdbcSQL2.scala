package com.minsub.jdbc.scalikejdbc

import java.sql.DriverManager

import scalikejdbc._

object ScalikejdbcSQL2 extends App {
  val url = "jdbc:as400://203.242.35.200;"
  val id = "DPBIZJMS"
  val pw = "wlalstjq2"

  //SETTINGS
  val settings = ConnectionPoolSettings(
    initialSize = 5,
    maxSize = 20,
    connectionTimeoutMillis = 3000L,
    validationQuery = "SELECT 1 FROM SYSIBM.SYSDUMMY1"
  )

  DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver())
  ConnectionPool.singleton(url, id, pw, settings)  // Apache Commons DBCP by default.


  implicit val session: DBSession = AutoSession

  val list = SQL("SELECT * FROM PLIBBP.GBPATEST1").map(_.toMap).list().apply()

  println(list)


}
