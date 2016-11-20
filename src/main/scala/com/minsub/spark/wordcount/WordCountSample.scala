package com.minsub.spark.wordcount

import org.apache.spark.{SparkConf, SparkContext}

object WordCountSample {
  def main(args : Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("wordCount")
    val sc = new SparkContext(conf)

    val fileRDD = sc.textFile("static/README_SPARK.md")
    fileRDD.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .filter(_._2 > 5)
      .foreach(tuple => println(tuple._1, tuple._2))
  }
}
