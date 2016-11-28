package com.minsub.spark.advance

import java.io.StringReader

import au.com.bytecode.opencsv.CSVReader
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jiminsub on 2016. 11. 20..
  */
object AccumulatorSample {
  val PATH = "file:/Users/jiminsub/workspace/ScalaMaven/"

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("accumulator1")
    val sc = new SparkContext(conf)

    // read file
    val fileRDD = sc.textFile(PATH + "static/README_SPARK.md")

    val blankLines = sc.longAccumulator("blankLines")

    fileRDD.flatMap(line => {
      if (line == "") {
        blankLines.add(1)
      }
      line.split(" ")
    }).foreach(println(_))

    println("blankLines in Accumulator: " + blankLines.value)

  }

}
