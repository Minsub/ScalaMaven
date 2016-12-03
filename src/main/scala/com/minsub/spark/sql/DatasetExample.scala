package com.minsub.spark.sql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{SQLContext, SparkSession}


/**
  * Created by jiminsub on 2016. 12. 3..
  */
object DatasetExample {

  case class Person(name:String, age:Long, address:String, phone:String)

  def main(args: Array[String]) = {
    // Create Spark SQL
    val spark = SparkSession.builder()
      .master("local")
      .appName("SparkSQL sample2")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    import spark.implicits._


    val personDS = Seq(Person("MS", 33, "Korea","010-1111-2222")).toDS()
    personDS.show()

    val numberDS = Seq(1,2,3,4,5).toDS()
    println(numberDS.map(_ + 1).collect().toList)

    val jsonDS = spark.read.json("static/dataset.json").as[Person]
    jsonDS.show()

  }
}
