package org.fig
package service

import model.Watch

import org.fig.repo.WatchRepo

import scala.annotation.unused

trait WatchService {

  def fetchWatchList(): List[Watch]

  def saveWatch(watch: Watch): Watch

}


case class WatchServiceImpl(watchRepo: WatchRepo) extends WatchService {

  override def fetchWatchList(): List[Watch] = {
    implicit val orderingByPreferenceAsc: Ordering[Watch] = Ordering.by((w: Watch) => w.preference)
    val remoteList = watchRepo.findAll()
    //remoteList.sortWith((w1,w2) => w1.preference < w2.preference)
    remoteList.sorted
  }

  override def saveWatch(watch: Watch): Watch = {
    val remoteList = fetchWatchList()
    if (remoteList.isEmpty) {
      watchRepo.save(List(watch))
    } else {
      val partitionedList: (List[Watch], List[Watch]) = remoteList.partition(remote => remote.preference < watch.preference)
      val updatedRemotes = partitionedList._2.map(remoteHigher => remoteHigher.copy(preference = remoteHigher.preference + 1))
      val updatedList = partitionedList._1 ++ List(watch) ++ updatedRemotes
      watchRepo.save(updatedList)
    }
    watch
  }


}
