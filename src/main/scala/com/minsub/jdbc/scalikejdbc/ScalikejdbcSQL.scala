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
  // #1
  val name2: Option[String] = DB readOnly { session =>
    session.single("SELECT 'Sing' COL1 FROM SYSIBM.SYSDUMMY1") { rs => rs.string("COL1") }
  }
  //println("direct session result #1: " + name2.get)

  // #2
  implicit val session = DB.readOnlySession()
  val name3: Option[String] = SQL("SELECT TSTTRD FROM PLIBBP.GBPATEST1 WHERE TSTTRD=?").bind("US").map(rs => rs.string(1)).single.apply()
  session.close()
//  println("direct session result #2: " + name3.get)


  case class Trade(name: String, idx: Int)
  val mapper = (rs: WrappedResultSet) => Trade(rs.string("name"), rs.int("idx"))

  DB autoCommit  { implicit session =>
    // SELECT
    val all: List[Trade] = SQL("SELECT TSTTRD name, 1 idx FROM PLIBBP.GBPATEST1").map(mapper).list.apply()
    val first: Option[Trade] = SQL("SELECT TSTTRD name, 1 idx FROM PLIBBP.GBPATEST1").map(mapper).first.apply()
    val selected: Option[Trade] = SQL("SELECT TSTTRD name, 1 idx FROM PLIBBP.GBPATEST1 WHERE TSTTRD=? and TSTBND=?")
                                     .bind("EU","W").map(mapper).single.apply()
    println(s"all => \n $all")
    println(s"first => \n $first")
    println(s"selected => \n $selected")

    // DELETE
    SQL("DELETE FROM PLIBBP.GBPATEST1 WHERE TSTTRD=?").bind("US").update.apply()
    SQL("DELETE FROM PLIBBP.GBPATEST1 WHERE TSTBND=?").bind("B").update.apply()

    // INSERT
    SQL("INSERT INTO PLIBBP.GBPATEST1 values (?, ?, ?)").bind("US","E","SIN").update.apply()

    // excute
    //SQL("create table company (id integer primary key, name varchar(30))").execute.apply()
  }

  // Batch API ( java.sql.PreparedStatement#executeBatch() )
  DB localTx { implicit session =>
    // placeholder
    val batcharams1: Seq[Seq[Any]] = (1 to 3).map(i => Seq("T"+i, "B" ,"KO" + i))
    SQL("INSERT INTO PLIBBP.GBPATEST1 values (?, ?, ?)").batch(batcharams1: _*).apply()

    // bind by name
    val batchParams2: Seq[Seq[(Symbol, Any)]] =  (4 to 6).map(i => Seq('trd -> ("T"+i), 'bnd -> "B"))
    SQL("INSERT INTO PLIBBP.GBPATEST1 (TSTTRD, TSTBND) values ({trd}, {bnd})").batchByName(batchParams2: _*).apply()
  }
}
