package org.demo.spark.main

import scala.annotation.tailrec

object RamerDouglasPeuckerReduction {

  val epsilon = 0.002

  def reduce(points: List[GPXData], startIndex: Int, endIndex: Int): List[GPXData] = {

    @tailrec
    def calcMaxDistance(index: Int, maxDist: Double, farthestIndex: Int): Tuple2[Double, Int] = {

      if (index < endIndex) {
        val distance = perpendicularDistance(points(startIndex), points(endIndex),
          points(index))
        if (distance > maxDist) {
          calcMaxDistance(index + 1, distance, index)
        } else {
          calcMaxDistance(index + 1, maxDist, farthestIndex)
        }
      } else {
        (maxDist, farthestIndex)
      }
    }
    val dist = calcMaxDistance(startIndex, 0, endIndex)
    val maxDistance = dist._1
    val index = dist._2

    if (maxDistance > epsilon && index != 0) {
      val upperResults = reduce(points, startIndex, index)
      val lowerResults = reduce(points, index + 1, endIndex)
      (upperResults.distinct ++ lowerResults.distinct)
    } else {
      List(points(startIndex), points(endIndex))
    }
  }

  private def perpendicularDistance(firstPoint: GPXData, lastPoint: GPXData, point: GPXData) = {

    val area = Math.abs(.5 * (firstPoint.lat * lastPoint.lon + lastPoint.lat *
      point.lon + point.lat * firstPoint.lon - lastPoint.lat * firstPoint.lon - point.lat *
      lastPoint.lon - firstPoint.lat * point.lon));
    val base = Math.sqrt(Math.pow(firstPoint.lat - lastPoint.lat, 2) +
      Math.pow(firstPoint.lon - lastPoint.lon, 2));
    val height = area / base * 2;

    height
  }

}