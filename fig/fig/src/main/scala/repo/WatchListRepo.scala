package org.fig
package repo

import model.WatchList

trait WatchListRepo {

  def findAll() : List[WatchList]

  def save(watchList: List[WatchList]) : List[WatchList]

}

object WatchListRepo extends WatchListRepo {

  var storedWatchList : List[WatchList] = List()

  override def findAll(): List[WatchList] = storedWatchList

  override def save(watchList: List[WatchList]): List[WatchList] = {
    storedWatchList = watchList
    watchList
  }

}

