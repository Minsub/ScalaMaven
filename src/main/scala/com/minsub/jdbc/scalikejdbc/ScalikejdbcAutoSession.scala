package com.minsub.jdbc.scalikejdbc

import java.sql.DriverManager

import scalikejdbc._

object ScalikejdbcAutoSession extends App {
  val url = "jdbc:as400://203.242.35.200;"
  val url_pro = "jdbc:as400://203.242.32.18;"
  val url_dev = "jdbc:as400://203.242.35.200;"
  val id = "DPBIZJMS"
  val pw = "wlalstjq2"

  DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver())
  ConnectionPool.singleton(url, id, pw)
  // Apache Commons DBCP by default.

  // for multiple DB
//  ConnectionPool.add("pro", url_pro, id, pw)
//  ConnectionPool.add("dev", url_dev, id, pw)


  val result = find("US")
  println("result: " + result)

  // AutoSession: readOnly -> executeQuery(), autoCommit -> execute()
  def find(trade: String)(implicit session: DBSession = AutoSession): Option[String] = {
    SQL("SELECT TSTTRD FROM PLIBBP.GBPATEST1 WHERE TSTTRD= ?").bind(trade).map(rs => rs.string(1)).single.apply()
  }

  // for multiple DB
  def findBySelectedDB(trade: String)(implicit session: DBSession = NamedAutoSession("dev")): Option[String] = {
    SQL("SELECT TSTTRD FROM PLIBBP.GBPATEST1 WHERE TSTTRD= ?").bind(trade).map(rs => rs.string(1)).single.apply()
  }
}
