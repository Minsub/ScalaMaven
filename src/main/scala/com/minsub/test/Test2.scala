package com.minsub.test

/**
  * Created by kakao on 2017. 7. 7..
  */
object Test2 extends App {
  val s = "79855627";
  
  val sp = s.split(",")
  
  println(sp.head.trim.toInt)
  
  
  class Car(brand: String) {
    def isNew = true
    def getBrand() = brand
    def print = println(s"brand: ${brand}")
  }

  val bmw = new Car("BMW")
//  val bmw: Car = null
  
  println {
    Option(bmw) match {
      case Some(car) if car.isNew => true
      case _ => false
    }
  } 

  println {
    bmw match {
      case c if c.getBrand() == "BMW" => true
      case _ => false
    }
  }
  
  
  def doit(x:Int, f:Int => Int) = {
    f(x)
  }
  
  println { doit(5, (x:Int) => x * x) }
}
