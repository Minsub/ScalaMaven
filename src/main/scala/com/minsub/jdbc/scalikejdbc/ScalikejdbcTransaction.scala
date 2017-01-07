package com.minsub.jdbc.scalikejdbc

import java.sql.DriverManager

import scalikejdbc._

object ScalikejdbcTransaction extends App {
  val url = "jdbc:as400://203.242.35.200;"
  val id = "DPBIZJMS"
  val pw = "wlalstjq2"

  DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver())
  ConnectionPool.singleton(url, id, pw)  // Apache Commons DBCP by default.

  val conn = DB(ConnectionPool.borrow())
  conn.begin()

  DB withinTx  { implicit session =>
    // DELETE
    SQL("DELETE FROM PLIBBP.GBPATEST1 WHERE TSTTRD=?").bind("US").update.apply()

    // INSERT
    SQL("INSERT INTO PLIBBP.GBPATEST1 values (?, ?, ?)").bind("US","E","SIN").update.apply()
  }

  conn.commit()
  conn.close()
}
