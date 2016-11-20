package com.minsub.spark.wordcount

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jiminsub on 2016. 11. 13..
  */
object RDDSample {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("rddSample")
    val sc = new SparkContext(conf)

    val rdd = sc.textFile("static/log.txt")
    val rddError = rdd.filter(line => line.contains("ERROR"))
    val rddWarn = rdd.filter(line => line.contains("WARN"))

    val rddBase = rddError.union(rddWarn)

    //  Output Console
    rddBase.foreach(println(_))
  }

}
