package com.pos
package cache

import scala.collection.mutable

trait Cache {

  def get(key: Integer) : Integer

  def put(key: Integer, value: Integer): Unit

}

class LRUCache(capacity: Integer) extends Cache {

  class Key(val key: Integer, val order: Integer){

    //def canEqual(a: Any) = a.isInstanceOf[Key]

    override def hashCode(): Int = key.hashCode()

    override def equals(obj: Any): Boolean = {
      obj match {
        case that: Key => {
          that.key equals key
        }
        case _ => false
      }
    }
  }

  implicit val order: Ordering[Key] = Ordering.by((i: Key) => i.order)

  val cache = mutable.TreeMap[Integer,Integer]()

  var leastRecentlyUsedKey : Option[Int] = None

  override def get(key: Integer): Integer = cache.getOrElse(key,-1)

  override def put(key: Integer, value: Integer): Unit = {
    if (cache.isEmpty) {
      cache.put(key,value)
      leastRecentlyUsedKey =
    } else {

    }

  }


}
