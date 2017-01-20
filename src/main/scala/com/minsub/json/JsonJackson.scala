package com.minsub.json

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

object JsonJackson extends App {

  case class Person(name: String, age: Int, kids: List[String])

  val person1 = """{
  "name": "Joe Doe",
  "age": 45,
  "kids": ["Frank", "Marta", "Joan"]
}"""

  val person2 = """{
  "name": "Bob ann",
  "age": 23,
  "kids": []
}"""

  // default JSON parser (to Map)
  val person = scala.util.parsing.json.JSON.parseFull(person1)
  println(person)

  // Jackson parser
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  // to Map
  val myMap = mapper.readValue[Map[String,Any]](person1)
  println(myMap)

  // to Case Class
  val myPerson = mapper.readValue(person1, classOf[Person])
  println(myPerson)

  // Case class to String
  val stringPerson = mapper.writeValueAsString(myPerson)
  println(stringPerson)

  // Map to String
  val stringPerson2 = mapper.writeValueAsString(myMap)
  println(stringPerson2)
}
