package com.minsub.spark.sql

import org.apache.spark.sql.SparkSession


/**
  * Created by jiminsub on 2016. 12. 3..
  */
object DatasetInferringExample {

  case class Person(name:String, age:Long, address:String, phone:String)

  def main(args: Array[String]) = {
    // Create Spark SQL
    val spark = SparkSession.builder()
      .master("local")
      .appName("SparkSQL sample3")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    import spark.implicits._

    // read text file (Inferring Schema)
    val textDF = spark.sparkContext
      .textFile("static/people.txt")
      .map(_.split(","))
      .map(attr => Person(attr(0), attr(1).trim().toLong, "", ""))
      .toDF()
    textDF.createOrReplaceTempView("people")

    val peopleDF = spark.sql("SELECT * FROM people")
    peopleDF.show()

    peopleDF.map(p => "Name: " + p(0)).show()

    peopleDF.map(p => "Name: " + p.getAs[String]("name")).show()

  }
}
