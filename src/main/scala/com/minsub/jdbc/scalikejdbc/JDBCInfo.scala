package com.minsub.jdbc.scalikejdbc

import java.sql.DriverManager

object JDBCInfo {
    val URL = "jdbc:mysql://127.0.0.1:3306/test"
    val URL2 = "jdbc:mysql://127.0.0.1:3306/test2"
    val ID = "root"
    val PW = "wlalstjq1"

  def initDriver(): Unit = {
    Class.forName("com.mysql.jdbc.Driver");
  }
}
