package org.demo.spark.main

import org.junit.Test
import org.junit.Before
import org.junit.Assert._
import java.util.Random;

class RamerDouglasPeuckerReductionTest {

  var points: List[GPXData] = _
  val numberOfElem = 100
  val maxRandomVal = 10000
  val randomGenerator = new Random()

  @Before def init() {
    points = initList(numberOfElem)
  }

  def initList(i: Int): List[GPXData] = {
    if (i == 1) {
      return List(new GPXData(i, i))
    } else {
      val p = new GPXData(randomGenerator.nextInt(maxRandomVal), randomGenerator.nextInt(maxRandomVal))
      return p :: initList(i - 1)
    }
  }

  @Test def testAlgo() {
    val reducedPoints = RamerDouglasPeuckerReduction.reduce(points, 0, 99)
    assert(reducedPoints.size > 2)
    assert(reducedPoints.size < points.size)
  }

}