package com.minsub.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jiminsub on 2016. 11. 20..
  */
object Test {
  val PATH = "file:/Users/jiminsub/workspace/ScalaMaven/"

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("sparkTest")
    val sc = new SparkContext(conf)


    val filesRDD = sc.wholeTextFiles("static")
    println("whileTextFile: " + filesRDD.collect().mkString(","))

  }

}
