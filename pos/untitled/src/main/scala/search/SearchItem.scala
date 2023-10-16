package com.pos
package search

trait SearchItem {

  /*
      Giving an array of words and a regex it returns all the words that match that regex
      and how many times the word exist in the dictionary
   */
  def findMatch(regex: String, dictionary: Array[String]): Set[Item]
  
}

object SearchItem extends SearchItem {
  override def findMatch(regex: String, dictionary: Array[String]): Set[Item] = {
    dictionary.toList.filter(_.matches(regex)).groupBy(w => w)
      .map(tuple => Item(tuple._1, tuple._2.size)).toSet
  }
}
