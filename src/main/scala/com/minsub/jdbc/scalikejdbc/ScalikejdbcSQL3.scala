package com.minsub.jdbc.scalikejdbc

import scalikejdbc._

object ScalikejdbcSQL3 extends App {
  Class.forName("com.mysql.jdbc.Driver")
  ConnectionPool.singleton(JDBCInfo.URL, JDBCInfo.ID, JDBCInfo.PW)

  implicit val session: DBSession = AutoSession

  val id = sql"""
    SELECT id FROM {} WHERE id=?
    """.bind("test1").map(rs => rs.string(1)).single.apply()

  println("id: " + id.get)
}
