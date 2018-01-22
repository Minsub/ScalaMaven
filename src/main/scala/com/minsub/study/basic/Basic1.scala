package com.minsub.study.basic

import scala.util.parsing.json.JSON

/**
  * Created by jiminsub on 2018. 1. 1..
  */
object Basic1 extends App {

  case class Animal(name: String)
  
  val dog = Animal("dog")
  val cat = Animal("cat")
  
  println(dog.name)
  
  
  def isBite(animal: Animal) = {
    animal match {
      case animal if animal.name == "cat" => true
      case ani: Animal => false
      case Animal("dog") => true
      case _ => false
    }
  }
  
  println(s"${dog.name} => isBite: ${isBite(dog)}")
  println(s"${cat.name} => isBite: ${isBite(cat)}")
  
  
  val map = Map("A" -> 1, "B" -> 2)
  val value = map.get("A")
  
  if (value.isDefined) {
    println(value.get)
  } 
  
  
  println(value.map(_ + 1).getOrElse(0))
  
  val either: Either[String, Int] = Left("string")
  
  println(either.right.getOrElse(-1))
  
  println(Range(1, 10).map { 
    case i if i < 5 => i * 10
    case i if i < 10 => i * 20
  } .toList)

  println(Range(1, 10).map(i => {
    i match {
      case i if i < 5 => i * 10
      case i if i < 10 => i * 20
    }
  }).toList)
  
  val json = JSON.parseFull("""{"name":"gray","age":33,"address":{"zip":12345,"nation":"korea"}}""")
  println(json)
  
  println { "THE END" }
    
}
