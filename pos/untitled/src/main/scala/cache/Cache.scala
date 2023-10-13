package com.pos
package cache

import scala.collection.mutable

trait Cache {

  def get(key: Int): Int

  def put(key: Int, value: Int): Unit

}

class LRUCache(capacity: Integer) extends Cache {

  case class Pair(val value: Int, val position: Int)

  // key is the position
  // value is the key from values Table
  val positionKeyMap = scala.collection.mutable.Map[Int, Int]()

  // key is the key, value is the Pair
  val keyValuesMap = scala.collection.mutable.Map[Int, Pair]()

  var oldestPosition: Int = 0
  var newestPosition: Int = 0

  override def get(key: Int): Int = {
    newestPosition += 1
    val value = keyValuesMap.get(key)
    if (value.isEmpty) {
      -1
    } else {
      val existingPair = value.get
      val newPair = Pair(existingPair.value, newestPosition)
      val existingPosition = existingPair.position
      positionKeyMap.remove(existingPosition)
      positionKeyMap.put(newestPosition, key)
      keyValuesMap.update(key, newPair)
      if (oldestPosition == existingPosition) {
        oldestPosition += 1
      }
      existingPair.value
    }
  }

  override def put(key: Int, value: Int): Unit = {
    newestPosition += 1
    val newPair = Pair(value, newestPosition)
    val mapValue = keyValuesMap.get(key)
    if (mapValue.isEmpty) {
      if (keyValuesMap.size == capacity) {
        // need to evict
        val oldestKey = positionKeyMap.remove(oldestPosition).get
        keyValuesMap.remove(oldestKey)
        positionKeyMap.put(newestPosition, key)
        keyValuesMap.put(key, newPair)
      } else {
        // just add
        positionKeyMap.put(newestPosition, key)
        keyValuesMap.put(key, newPair)
      }
    } else {
      // just update
      val existingPair = mapValue.get
      val existingPosition = existingPair.position
      positionKeyMap.remove(existingPosition)
      positionKeyMap.put(newestPosition, key)
      keyValuesMap.update(key, newPair)
      if (oldestPosition == existingPosition) {
        oldestPosition += 1
      }
    }
  }

}
