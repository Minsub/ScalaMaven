package com.minsub

import scala.collection.mutable

/**
 * @author ${user.name}
 */
object App {
  
  def foo(x : Array[String]) = x.foldLeft("")((a,b) => a + b)
  
  def main(args : Array[String]) {
    println( "Hello World!" )
    println("concat arguments = " + foo(args))

    val set = mutable.Set(1,2,3)

    set.add(5)

    println(set)
  }

}
