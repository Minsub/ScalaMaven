package com.minsub.spark

import org.apache.spark.{SparkConf, SparkContext}

object WordCountSample {
  def main(args : Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("wordCount")
    val sc = new SparkContext(conf)

    val fileRDD = sc.textFile("README.md")
    fileRDD.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
      .foreach(tuple => println(tuple._1, tuple._2))
  }
}
