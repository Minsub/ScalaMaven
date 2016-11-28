package com.minsub.spark.advance

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jiminsub on 2016. 11. 20..
  */
object AccumulatorCustom {
  val PATH = "file:/Users/jiminsub/workspace/ScalaMaven/"

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("accumulatorCustom")
    val sc = new SparkContext(conf)

    // read file
    val fileRDD = sc.textFile(PATH + "static/README_SPARK.md")
    val maxAccumulator = MaxAccumulatorV2
    sc.register(maxAccumulator)

    fileRDD.flatMap(line => {
      val words = line.split(" ")
      maxAccumulator.add(words.length)
      words
    }).foreach(println(_))

    println("maxWordCount by each Line in Accumulator: " + maxAccumulator.value)

  }


}




