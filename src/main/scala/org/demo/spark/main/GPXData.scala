package org.demo.spark.main

@SerialVersionUID(10L)
class GPXData (latitude: Double, longitude: Double) extends Serializable{
  
  def this() {
    this(0, 0)
  }
  
  def lat = latitude
  def lon = longitude
  
  override def toString(): String = "(" + latitude + ", " + longitude + ")";
  
  override def equals(other: Any) = {
    other match {
      case gpxPt: GPXData => latitude == gpxPt.lat && longitude == gpxPt.lon
      case _ => false
    }
  }

}