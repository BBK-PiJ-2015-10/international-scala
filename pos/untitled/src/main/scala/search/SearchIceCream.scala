package com.pos
package search

import scala.collection.mutable.TreeMap

//https://medium.com/@danromans/search-code-challenge-breakdown-dadf33c0773d
trait SearchIceCream {

  def selections(array: Array[Int], budget:Int) : Set[(Int,Int)]

}

object SearchIceCream extends SearchIceCream {
  override def selections(input: Array[Int],budget : Int): Set[(Int, Int)] = {
    val costLocationsMap = TreeMap[Int,Int]()
    Range(0,input.length).foreach(pos => {
      costLocationsMap.put(input(pos),pos)
    })
    var priorAddLower = -1
    var priorAddedHigher = -1
    var end = input.length-1
    var start = 0
    val costs = costLocationsMap.keySet.toList
    while (start < end){
      val lower = costs(start)
      val higher = costs(end)
      val evaluator = (lower + higher) - budget
      evaluator match {
        case 0 =>
        case x if x > 0 =>
        case _ =>
      }

      //TODO:

    }

    val other = costs.


    //for (i <-0 to 4) do {
      //println(i)
    //}
    //for (i <- 0 to array.length-1) {
      //val elem = array[i]
    //}
    //array.foreach(cat => costLocationsMap.)

    Set()

  }

}
