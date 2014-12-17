package org.demo.spark.main

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.hadoop.conf.Configuration
import org.bson.BasicBSONObject

object DataSaver {
  val mongoUri = "mongodb://127.0.0.1:27017/gpxdata"
  val hadoopDummyFile = "file:///dummy"
}

class DataSaver {

  val sparkConf = new SparkConf()

  val config = new Configuration()
  config.set("mongo.output.uri", DataSaver.mongoUri)

  def save(gpxData: RDD[Any]) = {

    val sparkConf = new SparkConf()
      
    val geoJsonData = gpxData.map(data => {
      var bson = new BasicBSONObject()
      (null, bson)
    })

    geoJsonData.saveAsNewAPIHadoopFile(DataSaver.hadoopDummyFile, classOf[Any], classOf[Any], classOf[com.mongodb.hadoop.MongoOutputFormat[Any, Any]], config)
  }

}