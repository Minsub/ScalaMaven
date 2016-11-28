package com.minsub.spark.advance

import org.apache.spark.util.AccumulatorV2

import scala.collection.mutable

/**
  * Created by jiminsub on 2016. 11. 28..
  */
object MaxAccumulatorV2 extends AccumulatorV2[Long, Long] {
  val set = mutable.Set[Long]();
  def reset(): Unit = {
    set.clear()
  }

  def add(v: Long): Unit = {
    set.add(v)
  }

  override def isZero: Boolean = {
    set.isEmpty
  }

  override def copy(): AccumulatorV2[Long, Long] = {
    this
  }

  override def merge(other: AccumulatorV2[Long, Long]): Unit = {

  }

  override def value: Long = {
    set.max
  }
}