package com.minsub.spark.etc

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.DeserializationFeature
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jiminsub on 2016. 11. 20..
  */
object DataIO {
  val PATH = "file:/Users/jiminsub/workspace/ScalaMaven/"

  case class Person(name: String, lovesPandas: Boolean)

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("dataIO")
    val sc = new SparkContext(conf)

    // read files from folder (파일명, word 갯수)
    val filesRDD = sc.wholeTextFiles(PATH + "static/*.*")
    val wordcountRDD = filesRDD.flatMapValues(_.split(" "))
      .mapValues(x => 1).reduceByKey(_ + _)
    println("whileTextFile: " + wordcountRDD.collect().mkString(","))

    // save as Text file
    //wordcountRDD.saveAsTextFile("static/output/wordCountFiles.txt")

    // JSON
    val jsonRDD = sc.textFile("static/sample.json")
    val result = jsonRDD.mapPartitions(records => {
      val mapper = new ObjectMapper with ScalaObjectMapper
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      mapper.registerModule(DefaultScalaModule)

      records.flatMap(record => {
        try {
          Some(mapper.readValue(record, classOf[Person]))
        } catch {
          case e: Exception => None
        }
      })
    }, true)

    println("JSON: " + result.collect().mkString(","))
  }

}
