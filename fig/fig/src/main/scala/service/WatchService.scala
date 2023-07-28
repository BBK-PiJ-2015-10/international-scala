package org.fig
package service

import model.Watch

import org.fig.repo.WatchRepo

trait WatchService {

  def fetchWatchList(): List[Watch]


}


case class WatchServiceImpl(watchRepo: WatchRepo) extends WatchService {
  override def fetchWatchList(): List[Watch] = {
    implicit val orderingByPreferenceAsc: Ordering[Watch] = Ordering.by((w: Watch) => w.preference)
    val remoteList = watchRepo.findAll()
    remoteList.sorted
  }

  

}
