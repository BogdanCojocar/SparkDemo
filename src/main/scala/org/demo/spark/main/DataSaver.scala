package org.demo.spark.main

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.hadoop.conf.Configuration
import org.bson.BasicBSONObject
import com.mongodb.BasicDBList

object DataSaver {
  val mongoUri = "mongodb://127.0.0.1:27017/geojson.out"
  val hadoopDummyFile = "file:///dummy"
}

class DataSaver {

  val sparkConf = new SparkConf()

  val config = new Configuration()
  config.set("mongo.output.uri", DataSaver.mongoUri)

  def save(gpxData: RDD[List[GPXData]]) = {

    val sparkConf = new SparkConf()

    val geoJsonData = gpxData.map(data => {
      val geoJson = new BasicBSONObject()
      var coordinates = new BasicDBList()

      data.foreach(gpxData => {
        val coordinate = new BasicBSONObject()
        coordinate.put("lat", gpxData.lat)
        coordinate.put("lon", gpxData.lon)
        coordinates.add(coordinate)
      })

      val geoJsonData = new BasicBSONObject()
      geoJsonData.put("type", "MultiPoint")
      geoJsonData.put("coordinates", coordinates)
      geoJson.put("loc", geoJsonData)
      (null, geoJson)
    })

    geoJsonData.saveAsNewAPIHadoopFile(DataSaver.hadoopDummyFile, classOf[Any], classOf[Any], classOf[com.mongodb.hadoop.MongoOutputFormat[Any, Any]], config)
  }
}