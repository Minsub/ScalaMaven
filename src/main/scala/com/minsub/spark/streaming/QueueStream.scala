package com.minsub.spark.streaming

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd._
import org.apache.spark.streaming._

import scala.collection.mutable.Queue

/**
  * Created by jiminsub on 2016. 12. 3..
  */
object QueueStream {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("Spark Streaming")
    val ssc = new StreamingContext(conf, Seconds(1))

    val queueRDD = new Queue[RDD[Int]]()

    val inputStream = ssc.queueStream(queueRDD)
    val mappedStream = inputStream.map(x => (x % 10, 1))
    val reduceStream = mappedStream.reduceByKey(_ + _)
    reduceStream.print()

    ssc.start()

    for (i <- 1 to 5) {
      queueRDD.synchronized {
        queueRDD += ssc.sparkContext.makeRDD(1 to 1000, 10)
      }

      Thread.sleep(1000)
    }

    ssc.stop()

  }

}
