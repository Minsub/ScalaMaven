package com.minsub.spark.streaming

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming._

import scala.collection.mutable.Queue

/**
  * Created by jiminsub on 2016. 12. 3..
  */
object QueueStream2 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("Spark Streaming")
    val ssc = new StreamingContext(conf, Seconds(1))

    val fileRdd = ssc.sparkContext.textFile("static/README_SPARK.md").persist(StorageLevel.MEMORY_ONLY)

    val queueRDD = new Queue[RDD[String]]()

    val inputStream = ssc.queueStream(queueRDD)
    val reduceStream = inputStream
          .flatMap(_.split(" "))
          .map((_,1))
          .reduceByKeyAndWindow(_+_, Seconds(5))
          .filter(_._2 > 4)

    reduceStream.print()

    ssc.start()

    for (i <- 1 to 5) {
      queueRDD.synchronized {
        queueRDD += fileRdd
      }

      Thread.sleep(1000)
    }

    ssc.stop()

  }

}
