package com.minsub.spark.io

import java.io.StringReader

import au.com.bytecode.opencsv.CSVReader
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jiminsub on 2016. 11. 20..
  */
object CsvIO {
  val PATH = "file:/Users/jiminsub/workspace/ScalaMaven/"

  case class Person(name: String, favoriteAnimal: String)

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("csvIO")
    val sc = new SparkContext(conf)

    // CSV FILE
    val csvRDD = sc.textFile(PATH + "static/sample.csv")
    val resultRDD = csvRDD.map(line => {
      val reader = new CSVReader(new StringReader(line))
      reader.readNext()
    })

    // show CSV
    resultRDD.foreach(arr => println(arr.mkString(",")))

    // show CSV
    //println("CSV: " + resultRDD.collect().mkString("\n"))

    // Create Person
    val csvAllRDD = sc.wholeTextFiles(PATH + "static/*.csv")
    val personRDD = csvAllRDD.flatMap{ case (_,txt) => {
        val reader = new CSVReader(new StringReader(txt))
        reader.readNext()
      }
    }

    // show PersonRDD
    personRDD.foreach(println(_))

  }

}
