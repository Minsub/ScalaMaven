package com.minsub.jdbc.scalikejdbc

import scalikejdbc._

object ScalikejdbcQueryDSL extends App {
  Class.forName("com.mysql.jdbc.Driver")
  ConnectionPool.singleton(JDBCInfo.URL, JDBCInfo.ID, JDBCInfo.PW)

  implicit val session: DBSession = AutoSession

  case class User(id:String, age:Int)
  object User extends SQLSyntaxSupport[User] {
    def apply(e: ResultName[User])(rs: WrappedResultSet): User = new User(rs.get(e.id), rs.get(e.age))

  }
  case class Groups(id:String, code: String, name:String)
  object Groups extends SQLSyntaxSupport[Groups]

  val u = User.syntax("u")
  val g = Groups.syntax("g")

  val id = sql"""
    SELECT id FROM ${User.as(u)} WHERE id=?
    """.bind("test1").map(rs => rs.string(1)).single.apply()

  println("id: " + id.get)

  val item: Option[User] =
  withSQL {
    select
      .from(User as u).leftJoin(Groups as g).on(u.id, g.id)
      .where.eq(u.id, "test1")
  }.map(User(u.resultName)).single().apply()



  println(item.get)

}
