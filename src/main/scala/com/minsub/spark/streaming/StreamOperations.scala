package com.minsub.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.rdd._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming._

import scala.collection.mutable.Queue

/**
  * Created by jiminsub on 2016. 12. 3..
  */
object StreamOperations {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("Spark Streaming")
    val ssc = new StreamingContext(conf, Seconds(1))

    val rdd = ssc.sparkContext.makeRDD(List(("A","100"),("B","200")))


    val queueRDD = new Queue[RDD[(String, String)]]()
    val queueStream = ssc.queueStream(queueRDD)

    val lines = ssc.socketTextStream("localhost", 9999)
    val joinStream = lines.flatMap(_.split(" ")).map((_, 1)).join(queueStream)

    joinStream.print()

    ssc.start()

    for (i <- 0 to 20) {
      queueRDD.synchronized {
        queueRDD += rdd
      }
      Thread.sleep(1000)
    }

    ssc.awaitTermination()

  }

}
