package com.rs.service

import com.rs.sink.client.ApiEntities.SubmissionRecord
import com.rs.source.client.ApiEntities.SourceRecord
import zio.{Queue, Task, UIO, ZIO, ZLayer}

import scala.collection.mutable.{HashMap, Map}

trait RecordProcessor {

  def processRecords(inputChannel: Queue[SourceRecord], outputChannel: Queue[SubmissionRecord]): ZIO[Any, Throwable, Boolean]

}


case class RecordProcessorImpl() extends RecordProcessor {

  val records: Map[String, SourceRecord] = HashMap[String, SourceRecord]()

  var counter: Int = 0

  override def processRecords(inputChannel: Queue[SourceRecord], outputChannel: Queue[SubmissionRecord]): ZIO[Any, Throwable, Boolean] = {
    for {
      _ <- ZIO.logInfo(s"Starting processing")
      sourceRecord <- inputChannel.take
      processResult <- processSourceRecord(sourceRecord, outputChannel)
      completed <- processResult match {
        case true => evaluateCompletion(outputChannel)
        case false => ZIO.attempt(false)
      }
    } yield completed
  }


  private def processSourceRecord(sourceRecord: SourceRecord, outputChannel: Queue[SubmissionRecord]): Task[Boolean] = {
    sourceRecord match {
      case SourceRecord(_, None) =>
        counter += 1
        ZIO.logInfo(s"Processor received a done records, updated counter to $counter") zipRight ZIO.attempt(true)
      case SourceRecord(_, Some(id)) =>
        records.remove(id) match {
          case None =>
            records.put(id, sourceRecord)
            ZIO.logInfo(s"Processor received a new record : $sourceRecord") zipRight ZIO.attempt(false)
          case Some(r) =>
            for {
              _ <- ZIO.logInfo(s"Processor received a matched record : $sourceRecord")
              matchedRecord = SubmissionRecord("joined", r.id.get)
              _ <- outputChannel.offer(matchedRecord)
              _ <- ZIO.logInfo(s"Processor submitted a joined record : $matchedRecord")
            } yield false
        }
    }
  }


  private def offerOrphans(list: List[SubmissionRecord], outputChannel: Queue[SubmissionRecord]) = {
    ZIO.foreach(list)(o => offerOrphan(o, outputChannel))
  }

  private def offerOrphan(record: SubmissionRecord, outputChannel: Queue[SubmissionRecord]) = {
    outputChannel.offer(record)
  }

  private def evaluateCompletion(outputChannel: Queue[SubmissionRecord]): ZIO[Any, Throwable, Boolean] = {
    if (counter >= 2) {
      val orphans = records.values.toList.map(r => SubmissionRecord("orphaned", r.id.get))
      ZIO.logInfo(s"Submitted ${orphans.length} orphansProcessing completed") zipRight offerOrphans(orphans, outputChannel) zipRight ZIO.succeed(records.clear()) zipRight ZIO.attempt(true)
    } else {
      ZIO.logInfo(s"Continue processing counter is at $counter") zipRight ZIO.attempt(false)
    }
  }

}


object RecordProcessor {

  def live(): ZLayer[Any, Nothing, RecordProcessor] = {
    ZLayer.fromZIO(ZIO.succeed(RecordProcessorImpl()))
  }
}