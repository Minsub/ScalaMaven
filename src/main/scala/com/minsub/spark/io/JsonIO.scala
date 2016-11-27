package com.minsub.spark.io

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jiminsub on 2016. 11. 20..
  */
object JsonIO {
  val PATH = "file:/Users/jiminsub/workspace/ScalaMaven/"

  case class Person(name: String, lovesPandas: Boolean)

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("jsonIO")
    val sc = new SparkContext(conf)

    // JSON
    val jsonRDD = sc.textFile("static/sample.json")
    val personRDD = jsonRDD.mapPartitions(records => {
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

    // show JSON
    println("JSON: " + personRDD.collect().mkString(","))

    // Save to JSON files
    personRDD.filter(_.lovesPandas).mapPartitions(records => {
      val mapper = new ObjectMapper with ScalaObjectMapper
      mapper.registerModule(DefaultScalaModule)
      records.map(mapper.writeValueAsString(_))
    }).saveAsTextFile(PATH+"static/sample_output.json")

  }

}
