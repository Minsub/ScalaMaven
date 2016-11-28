package com.minsub.spark.rdd

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jiminsub on 2016. 11. 20..
  */
object ExampleRDDNumberical {



  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("exampleRDDNumberical")
    val sc = new SparkContext(conf)

    // Create RDD
    val numberRDD = sc.parallelize(List(1, 2, 3, 4, 5, 6, 7, 8, 9))

    println("Max: " + numberRDD.max())
    println("Min: " + numberRDD.min())
    println("Count: " + numberRDD.count())
    println("mean: " + numberRDD.mean())
    println("sum: " + numberRDD.sum())
    println("variance: " + numberRDD.variance())
    println("Sample Variance: " + numberRDD.sampleVariance())
    println("stdev: " + numberRDD.stdev())
    println("Sample stdev: " + numberRDD.sampleStdev())

  }
}
