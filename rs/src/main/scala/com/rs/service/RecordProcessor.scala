package com.rs.service

import com.rs.sink.client.ApiEntities.SubmissionRecord
import com.rs.source.client.ApiEntities.SourceRecord
import zio.{Queue, UIO, ZIO, ZLayer}

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


  private def processSourceRecord(sourceRecord: SourceRecord, outputChannel: Queue[SubmissionRecord]) = {
    sourceRecord match {
      case SourceRecord(_, None) =>
        counter += 1
        ZIO.attempt(true)
      case SourceRecord(_, Some(id)) =>
        records.remove(id) match {
          case None =>
            records.put(id, sourceRecord)
            ZIO.attempt(false)
          case Some(r) =>
            for {
              offered <- outputChannel.offer(SubmissionRecord("joined", r.id.get))
            } yield !offered
        }
    }
  }

  private def evaluateCompletion(outputChannel: Queue[SubmissionRecord]): UIO[Boolean] = {
    if (counter >= 2) {
      records.values.toList.map(r => SubmissionRecord("orphan", r.id.get)).foreach(or => outputChannel.offer(or))
      records.clear()
      ZIO.logInfo(s"Processing completed") zipRight ZIO.attempt(true)
    } else {
      ZIO.logInfo(s"Continue processing counter is at $counter") zipRight ZIO.attempt(false)
    }
  }

}


object RecordProcessor {

  def layer(): ZLayer[Any, Nothing, RecordProcessor] = {
    ZLayer.fromZIO(ZIO.succeed(RecordProcessorImpl()))
  }
}