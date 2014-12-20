package org.demo.spark.main

object SparkDemo {
  def main(args: Array[String]) {
    
    val dataHandler = new GPXDataHandler()
    val data = dataHandler.parse()
    
    val dataSaver = new DataSaver()
    dataSaver.save(data)
  }
}