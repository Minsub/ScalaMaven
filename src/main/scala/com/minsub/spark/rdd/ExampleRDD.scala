package com.minsub.spark.rdd

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by jiminsub on 2016. 11. 20..
  */
object ExampleRDD {



  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("exampleRDD")
    val sc = new SparkContext(conf)

    // Create RDD
    val fileRDD = sc.textFile("static/log.txt")
    val lineRDD = sc.parallelize(List("i like an apple", "i am a boy"))
    val numberRDD = sc.parallelize(List(1,2,3,4,5))
    val number2RDD = sc.parallelize(List(2,4,4,6,8))
    val alphabetRDD = sc.parallelize(List("A","B","C"))

    /* Transformation Functions */
    // Map: 데이터를 받아 임의처리하고 같은 갯수로 (다른 타입이 될수있음) 으로 output
    println("Map: " + numberRDD.map(x => x * x).collect().mkString(","))

    // FlatMap: Flattening(펼치다) 의 개념으로 output으로 반복자(Array 등)을 반환하면 각 원소별로 데이터가 나옴.
    println("FlatMap: " + lineRDD.flatMap(line => line.split(" ")).collect().mkString(","))

    // Union: RDD를 합침. 데이터 타입이 같아야 함
    val log = "RDD(1,2,3,4,5) & RDD(2,4,4,6,8)"
    println(log + " Union: " + numberRDD.union(number2RDD).collect().mkString(","))

    // Distinct: 중복 제거
    println(log + " Distinct: " + number2RDD.distinct().collect().mkString(","))

    // Intersection: RDD 끼리의 Innter Join 개념
    println(log + " Intersection: " + numberRDD.intersection(number2RDD).collect().mkString(","))

    // Subtract: 항목에 없는 데이터를 제거
    println(log + " Subtract: " + numberRDD.subtract(number2RDD).collect().mkString(","))

    // Cartesian: Cartesian Product 개념
    println("RDD(A,B,C) & RDD(1,2,3,4,5) Cartesian: " + alphabetRDD.cartesian(numberRDD).collect().mkString(","))

    // Filter:
    println("RDD(1,2,3,4,5) Filter (x > 2): " + numberRDD.filter(_ > 2).collect().mkString(","))



    /* Action Functions */
    // reduce: 같은 타입으로 데이터를 누적해서 연산할 수 있음
    println("RDD(1,2,3,4,5) reduce(x+y): " + numberRDD.reduce(_ + _))

    // fold : reduce와 동일하나 제로 벨류값을 설정할 수 있음
    println("RDD(1,2,3,4,5) fold(0)(x+y): " + numberRDD.fold(0)(_ + _))

    //aggregate: Reduce 와 유사하나 다른 데이터 타입으로 가능 함
    println("RDD(1,2,3,4,5) aggregate(x+y): " + numberRDD.aggregate(0)(_ + _, _ + _))
    println("RDD(1,2,3,4,5) aggregate(sum, count): " +
      numberRDD.aggregate((0,0))((x,y)=>(x._1+y,x._2+1),(x,y)=>(x._1+y._1,x._2+y._2)))

    // countByValue: 데이터의 갯수
    println("RDD(2,4,4,6,8) countByValue: " + number2RDD.countByValue().toArray.toList.mkString(","))

    // take: n개의 데이터만 가져옴
    println("RDD(1,2,3,4,5) take(3): " + numberRDD.take(3).toList.mkString(","))

    // top: 상위 값중 n개를 가져옴
    println("RDD(2,4,4,6,8) top(3): " + number2RDD.top(3).toList.mkString(","))

    // takeOrdered: 특정 로직에 따라 n개의 갯수를 가져옴
    println("RDD(1,2,3,4,5) takeOrdered(2, 큰수): " +
      numberRDD.takeOrdered(2)(Ordering[Int].reverse).toList.mkString(","))

    implicit val sortCustom = new Ordering[Int] {
      override def compare(x: Int, y: Int): Int = { y.compare(x) }
    }

    println("RDD(1,2,3,4,5) takeOrdered(2, 큰수): " +
      numberRDD.takeOrdered(2)(sortCustom).toList.mkString(","))

  }
}
