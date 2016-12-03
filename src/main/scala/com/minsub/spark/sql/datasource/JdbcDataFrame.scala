package com.minsub.spark.sql.datasource

import org.apache.spark.sql.SparkSession


/**
  * Created by jiminsub on 2016. 12. 3..
  */
object JdbcDataFrame {


  def main(args: Array[String]) = {
    // Create Spark SQL
    val spark = SparkSession.builder()
      .master("local")
      .appName("SparkSQL")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    val jdbcDF = spark.read
      .format("jdbc")
      .option("url", "jdbc:postgresql://127.0.0.1:5432/education")
      .option("dbtable", "users")
      .option("user", "admin")
      .option("password", "admin")
      .load()

    jdbcDF.createOrReplaceTempView("users")

    spark.sql("SELECT * FROM users").show()

  }
}
