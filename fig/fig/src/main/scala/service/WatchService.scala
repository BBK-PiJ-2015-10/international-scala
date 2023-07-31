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
    val watchPreference = watch.preference
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

  def saveWatch2(watch: Watch): Watch = {
    var watchToSave = watch
    val watchPreference = watch.preference
    val remoteList = fetchWatchList()
    if (remoteList.isEmpty) {
      if (watchPreference != 1) {
        watchToSave = watch.copy(preference = 1)
      }
      watchRepo.save(List(watchToSave))
    } else {
      val partitionedList: (List[Watch], List[Watch]) = remoteList.partition(remote => remote.preference < watch.preference)
      val preferredList = partitionedList._1.sortWith((w1, w2) => w1.preference < w2.preference).reverse
      val preferredListHead = preferredList.headOption
      preferredListHead match {
        case Some(preferredWatch) => if (preferredWatch.preference != watchPreference - 1) {
          watchToSave = watch.copy(preference = preferredWatch.preference + 1)
        }
        case None =>
        //if (watchPreference != 1){
        // watchToSave = watch.copy(preference = 1)
        //}
      }
      val updatedRemotes = partitionedList._2.map(remoteHigherOrEqual => remoteHigherOrEqual.copy(preference = remoteHigherOrEqual.preference + 1))
      val updatedList = partitionedList._1 ++ List(watchToSave) ++ updatedRemotes
      watchRepo.save(updatedList)
    }
    watch
  }


}
