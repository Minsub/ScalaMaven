package com.minsub.spark.rdd

import org.apache.spark._
import org.apache.spark.storage.StorageLevel

/**
  * Created by jiminsub on 2016. 11. 20..
  */
object ExampleRDDPersist {

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("exampleRDDPersist")
    val sc = new SparkContext(conf)

    // Create RDD
    val numberRDD = sc.parallelize(List(1,2,3,4,5))

    val rdd = numberRDD.map(x => x * x)
    rdd.persist(StorageLevel.MEMORY_ONLY)

    println("count: " + rdd.count())
    println("Data: " + rdd.collect().mkString(","))

  }

}
