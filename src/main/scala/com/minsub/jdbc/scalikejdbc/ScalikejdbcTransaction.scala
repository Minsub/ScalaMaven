package com.minsub.jdbc.scalikejdbc

import java.sql.DriverManager

import scalikejdbc._

object ScalikejdbcTransaction extends App {
  val url = "jdbc:as400://203.242.35.200;"
  val id = "DPBIZJMS"
  val pw = "wlalstjq2"

  DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver())
  ConnectionPool.singleton(url, id, pw)
  // Apache Commons DBCP by default.

  // Transaction
  // #1
  val db = DB(ConnectionPool.borrow())
  try {
    db.begin()
    db withinTx { implicit session =>
      // DELETE
      SQL("DELETE FROM PLIBBP.GBPATEST1 WHERE TSTTRD=?").bind("US").update.apply()

      // INSERT
      SQL("INSERT INTO PLIBBP.GBPATEST1 values (?, ?, ?)").bind("US", "E", "SIN").update.apply()
    }
    db.commit()
  } finally { db.close() }



  // #2
  val count = DB localTx { implicit session =>
    // DELETE
    SQL("DELETE FROM PLIBBP.GBPATEST1 WHERE TSTTRD=?").bind("EU").update.apply()

    // INSERT
    SQL("INSERT INTO PLIBBP.GBPATEST1 values (?, ?, ?)").bind("EU","W","GBR").update.apply()
  }



}
