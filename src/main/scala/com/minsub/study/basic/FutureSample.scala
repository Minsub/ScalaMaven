package com.minsub.study.basic
import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.TimeUnit

import scala.concurrent._
import scala.concurrent.duration.Duration
import scala.util.Success



/**
  * Created by gray.ji on 2018. 1. 10..
  */
object FutureSample extends App {
  
  def getNumbers(): Future[Seq[Int]] = Future {
    TimeUnit.SECONDS.sleep(1)
//    Thread.sleep(3000)
    Range(1, 10)
  } 
  
  println("START")

  val list = getNumbers()

  println("FINISH GET NUMBERS")
  
//  list.foreach(println(_))
//
//  list.onComplete{
//    case Success(num) => println(s"Success: ${num}")
//    case _ => println("failed")
//  }
  
  val value = Await.result(list, Duration.Inf)
  println(value)

  println("WATING")
//  TimeUnit.SECONDS.sleep(5)
  println("END")
}
