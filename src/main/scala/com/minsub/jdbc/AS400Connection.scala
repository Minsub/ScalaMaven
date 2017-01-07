package com.minsub.jdbc

import java.sql.{Connection, DriverManager}

object AS400Connection {
  val url = "jdbc:as400://203.242.35.200;"
  val id = "DPBIZJMS"
  val pw = "wlalstjq2"

  def main(args: Array[String]): Unit = {
    DriverManager.registerDriver(new com.ibm.as400.access.AS400JDBCDriver())
    val con: Connection = DriverManager.getConnection(url, id, pw)

    // statement 생성
    val statement = con.createStatement()

    // 쿼리 날리기
    val result = statement.executeQuery("SELECT '1' COL1, 'A' COL2 FROM SYSIBM.SYSDUMMY1")

    // 결과값 꺼내기
    while( result.next() ){
      val test = result.getString(1) + " / " + result.getString(2);
      println(test)
    }

    // 커넥션 종료
    con.close()
  }
}
