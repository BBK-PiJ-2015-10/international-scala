package com.pos
package search

import scala.collection.mutable.TreeMap
import scala.collection.mutable.Set

//https://medium.com/@danromans/search-code-challenge-breakdown-dadf33c0773d
trait SearchIceCream {

  def selections(array: Array[Int], budget: Int): Set[(Int, Int)]

}

object SearchIceCream extends SearchIceCream {
  override def selections(input: Array[Int], budget: Int): Set[(Int, Int)] = {
    val result = Set[(Int, Int)]()
    val costFlavorMap: Map[Int, IndexedSeq[Int]] = Range(0, input.length).groupMap(pos => input(pos))(pos => pos)
    val orderedCosts = costFlavorMap.keySet.toList.sorted
    var end = orderedCosts.size - 1
    var start = 0
    var continue = true
    while (continue) {
      val lowerCost = orderedCosts(start)
      val higherCost = orderedCosts(end)
      val evaluator = (lowerCost + higherCost) - budget
      evaluator match {
        case 0 =>
          continue = false
          val lowerFlavors = costFlavorMap.get(lowerCost).get.map(_ + 1)
          val higherFlavors = costFlavorMap.get(higherCost).get.map(_ + 1)
          val flavors = lowerFlavors.map(lf => higherFlavors.map(hf => (hf, lf))).flatten
          flavors.foreach(flavor => result.add(flavor))
        case _ < 0 => start +=1
        case _ > 0 =>  end  -=1
      }
    }
    result
  }


}
