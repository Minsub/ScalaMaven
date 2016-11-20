package com.minsub.spark.rdd

import org.apache.spark._
import org.apache.spark.storage.StorageLevel

/**
  * Created by jiminsub on 2016. 11. 20..
  */
object ExampleRDDPair {

  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("exampleRDDPair")
    val sc = new SparkContext(conf)

    // Create RDD
    val pairRDD = sc.parallelize(List(("A",1), ("B",1), ("B", 2), ("C",5))).persist(StorageLevel.MEMORY_ONLY)
    val pair2RDD = sc.parallelize(List(("C",7), ("D",3))).persist(StorageLevel.MEMORY_ONLY)

    val log = "RDD((A,1), (B,1), (B,2), (C,5)) "


    /* Pair RDD Transformation function */

    // reduceByKey: Key별로 Reduce 연산
    println(log + " reduceByKey(sum): " + pairRDD.reduceByKey(_+_).collect().mkString(","))

    // groupByKey: key별로 Array로 각 value들이 합쳐짐 (key, [value1, value2 ...])
    println(log + " groupByKey(): " + pairRDD.groupByKey().collect().mkString(","))

    // mapValues: key변경 없이 value값만 변경
    println(log + " mapValues(x * x): " + pairRDD.mapValues(x => x * x).collect().mkString(","))

    // flatMapValues: key변경 없이 value를 반복자로 변경
    println(log + " flatMapValues(1 to x): " + pairRDD.flatMapValues(1 to _).collect().mkString(","))

    // keys: key값만 가져옴
    println(log + " keys(): " + pairRDD.keys.collect().mkString(","))

    // values: value값만 가져옴
    println(log + " values(): " + pairRDD.values.collect().mkString(","))

    // sortByKey: KEy로 sort해서 가져옴
    println(log + " sortByKey(descending): " + pairRDD.sortByKey(false).collect().mkString(","))

    val log2 = log + "RDD((C,7), (D,3)) "
    // subtractByKey: key를 기준으로 Subtract (겹치는 key가있는것들 제외)
    println(log2 + "subtractByKey: " + pairRDD.subtractByKey(pair2RDD).collect().mkString(","))

    // join: key가 같은 값끼리 합쳐짐
    println(log2 + "join: " + pairRDD.join(pair2RDD).collect().mkString(","))

    // reftOuterJoin:
    println(log2 + "leftOuterJoin: " + pairRDD.leftOuterJoin(pair2RDD).collect().mkString(","))

    // cogroup: 같은 key로 두 RDD를 그룹화 (key, ([rdd1's values], [rrd2's values])
    println(log2 + "cogroup: " + pairRDD.cogroup(pair2RDD).collect().mkString(","))



    // Average by Key
    val avgRDD = pairRDD.mapValues((_, 1))
                        .reduceByKey((x,y) => (x._1 + y._1, x._2 + y._2))
                        .mapValues(v => v._1 / v._2.toFloat)
    println(log + "average by key(mapValues + redueceByKey) => " + avgRDD.collect().mkString(","))


    // combineByKey:
    val combineRDD = pairRDD.combineByKey(
      (v) => (v, 1),
      (acc: (Int,Int), v) => (acc._1 + v, acc._2 + 1),
      (acc1: (Int, Int), acc2: (Int, Int)) => (acc1._1 + acc2._2, acc1._2 + acc2._2)
    ).map {case (key, value) => (key, value._1 / value._2.toFloat)}
    println(log + " average by key(combineByKey): " + combineRDD.collect().mkString(","))


    /* Pair RDD Action function */

    // countByKey:
    println(log + "countByKey: " + pairRDD.countByKey().toArray.mkString(","))

    // lookup: key에 해당하는 value를 Array로 반환
    println(log + "lookup: " + pairRDD.lookup("B").toArray.mkString(","))

  }

}
