package com.rs.service

import com.rs.sink.client.ApiEntities.SubmissionRecord
import com.rs.source.client.ApiEntities.SourceRecord
import zio.{Queue, ZIO}

import scala.collection.mutable.{Map, HashMap}

trait RecordProcessor {

  def processRecords(inputChannel: Queue[SourceRecord], outputChannel: Queue[SubmissionRecord]): ZIO[Any, Throwable, Boolean]

}


case class RecordProcessorImpl() extends RecordProcessor {

  val records: Map[String, SourceRecord] = HashMap[String, SourceRecord]()

  var counter: Int = 0


  private def processSourceRecored(sourceRecord: SourceRecord, outputChannel: Queue[SubmissionRecord]) = {
    sourceRecord match {
      case SourceRecord(_, None) =>
        counter += 1
        ZIO.attempt(true)
      case SourceRecord(_, Some(id)) =>
        records.get(id) match {
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

  //TODO if true evaluate value of counter


  override def processRecords(inputChannel: Queue[SourceRecord], outputChannel: Queue[SubmissionRecord]): ZIO[Any, Throwable, Boolean] = {
    for {
      _ <- ZIO.logInfo(s"Starting processing")
      inputRecord <- inputChannel.take

    } yield (true)
  }
}
