package com.minsub.study.basic.implicits

/**
  * Created by gray.ji on 2018. 1. 9..
  */
object ImplicitParameter extends App {
  case class MyPrompt(name: String)
  def greet(name: String)(implicit prompt: MyPrompt): Unit = {
    print(prompt.name)
    println(name)
  }
  
  val myPrompt = new MyPrompt("ubuntu> ")
  greet("twit")(myPrompt)
  
  implicit val implicitPrompt = new MyPrompt("my> ")
  greet("twit")
  greet("grrr...")
  greet("grrr...")(new MyPrompt("linux> "))

}
