package com.minsub.test

import scala.io.Source

/**
  * Created by gray.ji on 2018. 1. 16..
  */
object FileReadTest extends App {

//  val head = Source.fromFile("/Users/kakao/a.csv", "utf-8").getLines.toList.head
  val head = Source.fromFile("/Users/kakao/tms_error_test.csv", "utf-8").getLines.toList.head
  
  println(head)
  println(head.startsWith("\\uFEFF"))
  println(head.startsWith("uFEFF"))
  println(head.startsWith("\uFEFF"))
  println(head.charAt(0) == 65279)
  
  val a = head.replace("\uFEFF", "")
  val b = head.replace("\ufeff", "")
  println(a.toInt)
  println(b.toInt)
//  println(head.toInt)
  
  
  val s = "123,456,789"
  val arr = s.split(",")
  
  println((arr.head.toInt * 100 -> arr.drop(1).mkString(",").trim))
  
  
//
//  val iter = Source.fromFile("/Users/kakao/a.csv", "utf-8").getLines
//  val list = iter.grouped(100).foldLeft((0, 0)) { (t, groupedIter) => 
//    val m = groupedIter.filter(t => !t.trim.isEmpty).map { r => 
//      val s = r.split(",")
//      val head = remove(s.head)
//      (head.trim.toInt -> s.drop(1).mkString(",").trim)
//    }.toMap
//    
//    (1, 1)
//  }
//  
//  
//  def remove(s:String): String = {
//    if (s.startsWith("\\ufeff")) {
//      s.substring(1)
//    } else {
//      s
//    }
//  }

}
