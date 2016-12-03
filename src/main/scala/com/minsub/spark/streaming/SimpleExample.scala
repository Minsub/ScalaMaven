package com.minsub.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by jiminsub on 2016. 12. 3..
  */
object SimpleExample {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("Spark Streaming")
    val stream = new StreamingContext(conf, Seconds(2))

    val lines = stream.socketTextStream("localhost", 9999)

    val words = lines.flatMap(_.split(" "))
    val pairs = words.map((_, 1))
    val count = pairs.reduceByKey(_+_)

    count.print()

    stream.start()
    stream.awaitTermination()
  }

}
