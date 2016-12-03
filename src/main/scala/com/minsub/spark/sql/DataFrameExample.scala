package com.minsub.spark.sql


import org.apache.spark.sql.SparkSession

/**
  * Created by jiminsub on 2016. 12. 3..
  */
object DataFrameExample {

  def main(args: Array[String]) = {
    // Create Spark SQL
    val spark = SparkSession.builder()
      .master("local")
      .appName("SparkSQL sample1")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    // create json DataFrame
    val df = spark.read.json("static/dataset.json")
    df.show()

    // ORM
    //df.select("name").show()
    //df.filter("age > 5").show()
    //df.groupBy("age").count().show()


    // SQL
    df.createOrReplaceTempView("users")

    val sqlDF = spark.sql("SELECT * FROM users WHERE age=21 and name='Spark' ")
    sqlDF.show()
  }
}
