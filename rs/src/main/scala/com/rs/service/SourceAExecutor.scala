package com.rs.service

import com.rs.dto.Dto.{DoneRecord, OngoingRecord, Record}
import com.rs.source.client.ApiEntities.SourceRecord
import com.rs.source.client.SourceAClient
import zio.{Queue,ZIO, ZLayer}

trait SourceAExecutor {
  def fetchRecords(workBuffer: Queue[Record], controlBuffer: Queue[Boolean]): ZIO[Any, Throwable, Boolean]

}

case class SourceAExecutorImpl(sourceAClient: SourceAClient) extends SourceAExecutor {

  override def fetchRecords(workBuffer: Queue[Record], controlBuffer: Queue[Boolean]): ZIO[Any, Throwable, Boolean] = for {
    - <- ZIO.logInfo(s"Starting consumption of sourceA messages")
    sourceARecord <- sourceAClient.fetchRecord()
    result <- processRecord(workBuffer, controlBuffer, sourceARecord)
    _ <- ZIO.logInfo("Finalizing executor")
  } yield result

  def processRecord(workBuffer: Queue[Record], controlBuffer: Queue[Boolean], record: Option[SourceRecord]): ZIO[Any, Nothing, Boolean] = {
    if (record.isEmpty) {
      ZIO.logInfo(s"Ignoring an empty record for sourceA") zipRight ZIO.succeed(true)
    } else {
      val result = record match {
        case SourceRecord(status, None) =>
          val record = DoneRecord(status, "sourceA")
          for {
            _ <- workBuffer.offer(record)
            _ <- ZIO.logInfo(s"Offering a done record for sourceA and pausing consumption")
            cr <- controlBuffer.take
          } yield cr
        case SourceRecord(status, Some(id)) =>
          val record = OngoingRecord(status, id, "sourceA")
          for {
            or <- workBuffer.offer(record)
            _ <- ZIO.logInfo(s"Offering an ongoing record for sourceA")
          } yield or
      }
      result
    }
  }
}

object SourceAExecutor {

  def layer(): ZLayer[SourceAClient, Nothing, SourceAExecutor] = {
    ZLayer.fromFunction(SourceAExecutorImpl(_))
  }

}


