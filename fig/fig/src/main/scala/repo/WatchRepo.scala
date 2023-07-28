package org.fig
package repo

import model.Watch

trait WatchRepo {

  def findAll() : List[Watch]

  def save(watchList: List[Watch]) : List[Watch]

}

object WatchListRepo extends WatchRepo {

  var storedWatchList : List[Watch] = List()

  override def findAll(): List[Watch] = storedWatchList

  override def save(watchList: List[Watch]): List[Watch] = {
    storedWatchList = watchList
    watchList
  }

}

