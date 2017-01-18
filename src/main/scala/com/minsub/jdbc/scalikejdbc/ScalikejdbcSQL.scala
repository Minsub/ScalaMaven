package com.minsub.jdbc.scalikejdbc

import java.sql.{DriverManager, ResultSet}

import scalikejdbc._

object ScalikejdbcSQL extends App {
  Class.forName("com.mysql.jdbc.Driver")
  ConnectionPool.singleton(JDBCInfo.URL, JDBCInfo.ID, JDBCInfo.PW)

  val sqlSimple = "SELECT id FROM user LIMIT 1"
  // simple
  val name: Option[String] = DB readOnly { implicit session =>
    SQL(sqlSimple).map(rs => rs.string("id")).single.apply()
  }
  println("simple result: " + name.get)

  // directly DBSession #1
  val name2: Option[String] = DB readOnly { session =>
    session.single(sqlSimple) { rs => rs.string("id") }
  }
  println("direct session result #1: " + name2.get)

  // directly DBSession #2
  implicit val session = DB.readOnlySession()
  val name3: Option[String] = SQL(sqlSimple).map(rs => rs.string(1)).single.apply()
  session.close()
  println("direct session result #2: " + name3.get)


  case class User(id: String, age: Int)
  val mapper = (rs: WrappedResultSet) => User(rs.string("id"), rs.int("age"))

  DB autoCommit  { implicit session =>
    // SELECT
    val all: List[User] = SQL("SELECT * FROM user").map(mapper).list.apply()
    val first: Option[User] = SQL("SELECT * FROM user").map(mapper).first.apply()
    val selected: Option[User] = SQL("SELECT * FROM user WHERE id=? and age=?").bind("test1", 20).map(mapper).single.apply()
    println(s"all => \n $all")
    println(s"first => \n $first")
    println(s"selected => \n $selected")

    // DELETE
    SQL("DELETE FROM user WHERE id=?").bind("test2").update.apply()

    // INSERT
    SQL("INSERT INTO user values (?, ?)").bind("test2", 33).update.apply()

    // EXECUTE
    //SQL("create table company (id integer primary key, name varchar(30))").execute.apply()
  }

  // Batch API ( java.sql.PreparedStatement#executeBatch() )
  DB localTx { implicit session =>
    // placeholder
    val batcharams1: Seq[Seq[Any]] = (5 to 7).map(i => Seq("test"+i, i * 2))
    SQL("INSERT INTO user values (?, ?)").batch(batcharams1: _*).apply()

    // bind by name
    val batchParams2: Seq[Seq[(Symbol, Any)]] =  (8 to 9).map(i => Seq('id -> ("unknown"+i), 'age -> i * 3))
    SQL("INSERT INTO user(id,age) VALUES({id}, {age})").batchByName(batchParams2: _*).apply()
  }
}
