package com.minsub.io.file

import scala.io.Source

object FileIO extends App{
  val PATH = "src/main/resources/fileIO.csv"

  for (line <- Source.fromFile(PATH).getLines()) {
    println(line)
  }

}
