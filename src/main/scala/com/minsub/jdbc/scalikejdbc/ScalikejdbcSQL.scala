package com.minsub.jdbc.scalikejdbc

import java.sql.{DriverManager, ResultSet}

import scalikejdbc._

object ScalikejdbcSQL extends App {
  val url = "jdbc:as400://203.242.35.200;"
  val id = "DPBIZJMS"
  val pw = "wlalstjq2"

  DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver())
  ConnectionPool.singleton(url, id, pw)  // Apache Commons DBCP by default.

  // simpler
  val name: Option[String] = DB readOnly { implicit session =>
    SQL("SELECT 'A' COL1, 'B' COL2 FROM SYSIBM.SYSDUMMY1").map(rs => rs.string("COL1")).single.apply()
  }
  //println("simpler result: " + name.get)

  // directly DBSession
  val name2: Option[String] = DB readOnly { session =>
    session.single("SELECT 'Sing' COL1 FROM SYSIBM.SYSDUMMY1") { rs => rs.string("COL1") }
  }
  //println("direct session result: " + name2.get)

  case class Trade(name: String, idx: Int)
  val mapper = (rs: WrappedResultSet) => Trade(rs.string("name"), rs.int("idx"))

  DB autoCommit  { implicit session =>
    // query
    val all: List[Trade] = SQL("SELECT TSTTRD name, 1 idx FROM PLIBBP.GBPATEST1").map(mapper).list.apply()
    val first: Option[Trade] = SQL("SELECT TSTTRD name, 1 idx FROM PLIBBP.GBPATEST1").map(mapper).first.apply()
    val selected: Option[Trade] = SQL("SELECT TSTTRD name, 1 idx FROM PLIBBP.GBPATEST1 WHERE TSTTRD=? and TSTBND=?")
                                     .bind("EU","W").map(mapper).single.apply()
    println(s"all => \n $all")
    println(s"first => \n $first")
    println(s"selected => \n $selected")

    // DELETE
    SQL("DELETE FROM PLIBBP.GBPATEST1 WHERE TSTTRD=?").bind("US").update.apply()

    // INSERT
    SQL("INSERT INTO PLIBBP.GBPATEST1 values (?, ?, ?)").bind("US","E","SIN").update.apply()

    // execute
    //SQL("create table company (id integer primary key, name varchar(30))").execute.apply()
  }

}
