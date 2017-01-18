package com.minsub.jdbc.scalikejdbc

import scalikejdbc._

object ScalikejdbcTransaction extends App {
  Class.forName("com.mysql.jdbc.Driver")
  ConnectionPool.singleton(JDBCInfo.URL, JDBCInfo.ID, JDBCInfo.PW)

  // Transaction #1
  val db = DB(ConnectionPool.borrow())
  try {
    db.begin()
    db withinTx { implicit session =>
      // DELETE
      SQL("DELETE FROM user WHERE id=?").bind("test4").update.apply()

      // INSERT
      SQL("INSERT INTO user VALUES (?, ?)").bind("test4", 11).update.apply()
    }
    db.commit()
  } finally { db.close() }

  // Transaction #2
  val count = DB localTx { implicit session =>
    // DELETE
    SQL("DELETE FROM user WHERE id=?").bind("test5").update.apply()

    // INSERT
    SQL("INSERT INTO user VALUES (?, ?)").bind("test5", 15).update.apply()
  }
}
