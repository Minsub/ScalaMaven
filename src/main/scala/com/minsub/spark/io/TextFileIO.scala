package com.minsub.spark.io

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jiminsub on 2016. 11. 20..
  */
object TextFileIO {
  val PATH = "file:/Users/jiminsub/workspace/ScalaMaven/"

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("textFileIO")
    val sc = new SparkContext(conf)

    // TEXT FILE
    val filesRDD = sc.wholeTextFiles(PATH + "static/*.*")
    val wordcountRDD = filesRDD.flatMapValues(_.split(" "))
      .mapValues(x => 1).reduceByKey(_ + _)
    println("whileTextFile: " + wordcountRDD.collect().mkString(","))

    // save to Text file
    wordcountRDD.saveAsTextFile("static/output/wordCountFiles.txt")
  }

}
