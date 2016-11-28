package com.minsub.spark.advance

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jiminsub on 2016. 11. 20..
  */
object BroadcastVariableSample {
  val PATH = "file:/Users/jiminsub/workspace/ScalaMaven/"

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("broadcast1")
    val sc = new SparkContext(conf)

    // create rdd
    val characterRDD = sc.parallelize(List("A","B","C","D"))
    val broadcastWords = sc.broadcast(List("Apple","Banana","Caption"))

    characterRDD.map(w => {
      (w, lookupListPrefix(w, broadcastWords.value))
    }).foreach(println(_))

  }

  def lookupListPrefix(word:String, list: List[String]): String = {
    var result = ""
    list.foreach(w => {
      if (w.contains(word)) {
        result = w
      }
    })
    result
  }


}




