package com.minsub.jdbc.scalikejdbc

import java.sql.DriverManager

import scalikejdbc._

object ScalikejdbcSQL2 extends App {

  //SETTINGS
  val settings = ConnectionPoolSettings(
    initialSize = 5,
    maxSize = 20,
    connectionTimeoutMillis = 3000L,
    validationQuery = "SELECT 1"
  )

  Class.forName("com.mysql.jdbc.Driver")
  ConnectionPool.singleton(JDBCInfo.URL, JDBCInfo.ID, JDBCInfo.PW, settings)


  implicit val session: DBSession = AutoSession

  val list = SQL("SELECT * FROM PLIBBP.GBPATEST1").map(_.toMap).list().apply()

  println(list)


}
