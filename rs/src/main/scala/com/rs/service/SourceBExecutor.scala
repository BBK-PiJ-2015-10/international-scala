package com.rs.service

import com.rs.source.client.ApiEntities.SourceRecord
import com.rs.source.client.SourceBClient
import zio.{Queue, ZIO, ZLayer}

trait SourceBExecutor {

  def fetchRecords(workBuffer: Queue[SourceRecord], controlBuffer: Queue[Boolean]): ZIO[Any, Throwable, Boolean]

}

case class SourceBExecutorImpl(sourceBClient: SourceBClient) extends SourceBExecutor {

  override def fetchRecords(workBuffer: Queue[SourceRecord], controlBuffer: Queue[Boolean]): ZIO[Any, Throwable, Boolean] = for {
    - <- ZIO.logInfo(s"Starting consumption of sourceA messages")
    sourceARecord <- sourceBClient.fetchRecord()
    result <- processRecord(workBuffer, controlBuffer, sourceARecord)
    _ <- ZIO.logInfo("Finalizing executor")
  } yield result

  def processRecord(workBuffer: Queue[SourceRecord], controlBuffer: Queue[Boolean], record: Option[SourceRecord]): ZIO[Any, Nothing, Boolean] = {
    if (record.isEmpty) {
      ZIO.logInfo(s"Ignoring an empty record for sourceA") zipRight ZIO.succeed(true)
    } else {
      val result = record match {
        case SourceRecord(_, None) =>
          for {
            _ <- workBuffer.offer(record.get)
            _ <- ZIO.logInfo(s"Offering a done record for sourceA and pausing consumption")
            cr <- controlBuffer.take
          } yield cr
        case SourceRecord(_, Some(_)) =>
          for {
            or <- workBuffer.offer(record.get)
            _ <- ZIO.logInfo(s"Offering an ongoing record for sourceB")
          } yield or
      }
      result
    }
  }
}

object SourceBExecutor {

  def layer(): ZLayer[SourceBClient, Nothing, SourceBExecutor] = {
    ZLayer.fromFunction(SourceBExecutorImpl(_))
  }

}