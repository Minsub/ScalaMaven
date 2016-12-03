package com.minsub.spark.sql.datasource

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._


/**
  * Created by jiminsub on 2016. 12. 3..
  */
object SimpleSample {

  case class Person(name:String, age:String)

  def main(args: Array[String]) = {
    // Create Spark SQL
    val spark = SparkSession.builder()
      .master("local")
      .appName("SparkSQL")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()


    val peopleDF = spark.read.format("json").load("static/dataset.json")
    peopleDF.select("name", "age").filter("age = 21").write.format("parquet").save("static/out/people.parquet")

    println("COMPLETE SAVE to static/out/people.parquet")
  }
}
