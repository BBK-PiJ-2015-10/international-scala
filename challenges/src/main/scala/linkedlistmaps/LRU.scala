package linkedlistmaps

import com.typesafe.scalalogging.Logger

import java.time.LocalDateTime
import scala.collection.mutable
import scala.collection.mutable.Map

object LRU {

  case class Person(id: Int)

  case class Box(person: Person, timeStamp: LocalDateTime)

  private val logger = Logger(getClass.getName)

  private val maxCapacity = 10

  val cache: Map[Int, Person] = new mutable.HashMap[Int, Person]

  implicit val boxByOlderDate: Ordering[Box] = Ordering.by((b: Box) => b.timeStamp)

  private val cacheHistory: mutable.TreeMap[Int, Box] = new mutable.TreeMap[Int, Box]

  def fetch(id: Int): Person = {

    logger.info(s"Fetching person with id $id")
    // get the Person from cache
    val timestamp = LocalDateTime.now()
    val maybePerson = cache.get(id)
    maybePerson match {
      case Some(p) => {
        logger.info(s"Person with id $id found")
        // update cacheHistory
        cacheHistory.remove(id).get
        cacheHistory.put(id, Box(p, timestamp))
        p
      }
      case None => {
        logger.info(s"Person with id $id not found remote fetching person")
        val newPerson = Person(id)
        // update cache
        if (cache.size == maxCapacity) {
          // remove oldest
          val oldestBox = cacheHistory.head
          cacheHistory.remove(oldestBox._1)
          cache.remove(oldestBox._1)
          logger.info(s"Cache was at a capacity removed oldest entry id: ${oldestBox._1} box: ${oldestBox}")
          // add to cache and cacheHistory
          cache.put(id, newPerson)
          cacheHistory.put(id, Box(newPerson, timestamp))
          newPerson
        } else {
          // just add person to cache and cacheHistory
          cache.put(id, newPerson)
          cacheHistory.put(id, Box(newPerson, timestamp))
          newPerson
        }
      }
    }
  }


}


