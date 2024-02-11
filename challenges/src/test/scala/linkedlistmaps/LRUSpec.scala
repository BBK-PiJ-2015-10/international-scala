package linkedlistmaps

import org.scalatest.funsuite.AnyFunSuite

class LRUSpec extends AnyFunSuite {

  test("testing fetching first entry non existent") {
    val id = 1
    LRU.fetch(id)
    assert(LRU.cache.size == 1)
    assert(LRU.cache.contains(id))
  }

  test("testing fetching first entry existent") {
    val id = 1
    LRU.fetch(id)
    LRU.fetch(id)
    assert(LRU.cache.size == 1)
    assert(LRU.cache.contains(id))
  }

  test("testing fetching 11th entry non existent") {
    val firstId = 1
    Range.inclusive(1, 11).map(pId => LRU.fetch(pId))
    assert(LRU.cache.size == 10)
    assert(LRU.cache.contains(firstId) == false)
    assert(LRU.cache.contains(11))
  }

  test("testing fetching 10th twice entry non existent") {
    Range.inclusive(1, 10).map(pId => LRU.fetch(pId))
    val repeatingEntry = 5
    LRU.fetch(repeatingEntry)
    assert(LRU.cache.size == 10)
    assert(LRU.cache.contains(repeatingEntry))
    assert(LRU.cache.contains(1))
  }

}
