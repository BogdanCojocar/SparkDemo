package org.demo.spark.main

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.hadoop.io.Text
import org.apache.hadoop.streaming.StreamInputFormat
import org.apache.hadoop.mapred.JobConf
import org.apache.hadoop.streaming.StreamXmlRecordReader
import org.apache.hadoop.mapred.FileInputFormat
import scala.xml.XML

object GPXDataHandler {
  val streamRecorder = "org.apache.hadoop.streaming.StreamXmlRecordReader"
  val startTag = "<trkseg>"
  val endTag = "</trkseg>"
  val dataLocation = "file:////dir/to/data/*.gpx"
}

class GPXDataHandler {

  private def read(): RDD[(Text, Text)] = {

    val sparkConf = new SparkConf()

    val jobConf = new JobConf()
    jobConf.set("stream.recordreader.class", GPXDataHandler.streamRecorder)
    jobConf.set("stream.recordreader.begin", GPXDataHandler.startTag)
    jobConf.set("stream.recordreader.end", GPXDataHandler.endTag)

    FileInputFormat.addInputPaths(jobConf, GPXDataHandler.dataLocation)

    val sparkContext = new SparkContext(sparkConf)

    sparkContext.hadoopRDD(
      jobConf,
      classOf[StreamInputFormat],
      classOf[Text],
      classOf[Text])
  }

  def parse() = {
    val xmlReadData = read()
    val data = xmlReadData
      .map(_._1.toString)
      .map {
        xmlData =>
          val xml = XML.loadString(xmlData)
          val trackpts = xml \ "trkpt"
          val gpsData = trackpts.map(xmlNode =>
            new GPXData((xmlNode \ "@lat").text.toDouble, (xmlNode \ "@lon").text.toDouble))
          RamerDouglasPeuckerReduction.reduce(gpsData.toList, 0, gpsData.size - 1)
      }
    data
  }
}