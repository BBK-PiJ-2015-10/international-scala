package com.pos
package search

import org.scalatest.funsuite.AnyFunSuite

import scala.collection.mutable.TreeMap

class SearchIceCreamTest extends AnyFunSuite {

  test("An empty List should have size 0") {

    val testSet = TreeMap[Int,Int]()

    testSet.keySet

    assert(List.empty.size == 0)
  }


}
