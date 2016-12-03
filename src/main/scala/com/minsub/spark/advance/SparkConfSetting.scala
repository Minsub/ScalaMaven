package com.minsub.spark.advance

import org.apache.spark.{SparkConf, SparkContext}

object SparkConfSetting {
  def main(args : Array[String]): Unit = {
    val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName("wordCount")
    conf.set("spark.app.name", "MS Spark App")
    conf.set("spark.master", "local[4]")
    conf.set("spark.ui.port","3333")

    val sc = new SparkContext(conf)

    val fileRDD = sc.textFile("static/README_SPARK.md")
    fileRDD.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .filter(_._2 > 5)
      .foreach(tuple => println(tuple._1, tuple._2))
  }
}
