package com.minsub.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by jiminsub on 2016. 12. 3..
  */
object WordCountWindow {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("Spark Streaming")
    val ssc = new StreamingContext(conf, Seconds(1))

    val lines = ssc.socketTextStream("localhost", 9999)

    val linesFilterted = lines.transform(rdd => {
      rdd.filter(_.length <= 3)
    })

    linesFilterted.window(Seconds(5)).foreachRDD(rdd => {
      if (rdd.count() == 0) {
        println("Count is 0")
      } else {
        val count = rdd.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_)
        println(count.collect().mkString("\n"))
      }
    })

    ssc.start()
    ssc.awaitTermination()

    //nc -lk 9999
  }

}
