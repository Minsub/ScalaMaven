package com.minsub.spark.io

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.hadoop.io.{IntWritable, Text}

/**
  * Created by jiminsub on 2016. 11. 20..
  */
object SequenceFileIO {
  val PATH = "file:/Users/jiminsub/workspace/ScalaMaven/"

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("sequenceFileIO")
    val sc = new SparkContext(conf)

    // Sequence File
    val seqRDD = sc.sequenceFile(PATH + "static/sample.seq", classOf[Text], classOf[IntWritable])

    // show Seq. RDD
    seqRDD.foreach(println(_))

    // Save to Sequence File
    val pairRDD = sc.parallelize(List(("Apple",5),("Banana",7),("Caption", 3)))
    pairRDD.saveAsSequenceFile(PATH + "static/sequnceFile")

  }

}
