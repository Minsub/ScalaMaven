package com.minsub.spark.io

import java.sql.{Connection, DriverManager, ResultSet}

import org.apache.hadoop.io.{IntWritable, Text}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.JdbcRDD

/**
  * Created by jiminsub on 2016. 11. 20..
  */
object JdbcIO {
  val PATH = "file:/Users/jiminsub/workspace/ScalaMaven/"

  def createConnection() = {
    Class.forName("org.postgresql.Driver").newInstance()
    DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/education","admin", "admin")
  }

  def extractValues(r: ResultSet) = {
    (r.getString("uid"), r.getString("uname"))
  }



  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("jdbcIO")
    val sc = new SparkContext(conf)

    val sql = "select * from users limit ? offset ?"

    val jdbcRDD = new JdbcRDD(sc
              , createConnection
              , sql
              , lowerBound = 1
              , upperBound = 3
              , numPartitions = 2
              , mapRow = extractValues)

    println(jdbcRDD.collect().toList)



  }

  def sampleTest() = {
    val con = createConnection()
    val rs = con.createStatement().executeQuery("select * from users")

    while(rs.next()) {
      print(s" uid: ${rs.getString(1)} , uname: ${rs.getString(3)} ")
    }
  }

}
