package com.rs.service

import com.rs.source.client.ApiEntities.SourceRecord
import com.rs.source.client.SourceAClient
import zio.{Duration, Queue, ZIO, ZLayer}

trait SourceAExecutor {
  def fetchRecords(workBuffer: Queue[SourceRecord], controlBuffer: Queue[Boolean]): ZIO[Any, Throwable, Boolean]

}

case class SourceAExecutorImpl(sourceAClient: SourceAClient) extends SourceAExecutor {

  override def fetchRecords(workBuffer: Queue[SourceRecord], controlBuffer: Queue[Boolean]): ZIO[Any, Throwable, Boolean] = (for {
    - <- ZIO.logInfo(s"Starting consumption of sourceA messages")
    sourceARecord <- sourceAClient.fetchRecord()
    result <- processRecord(workBuffer, controlBuffer, sourceARecord)
    _ <- ZIO.sleep(Duration.fromMillis(2000))
    _ <- ZIO.logInfo("Finalizing executor")
  } yield result).retryN(3)

  def processRecord(workBuffer: Queue[SourceRecord], controlBuffer: Queue[Boolean], record: Option[SourceRecord]): ZIO[Any, Nothing, Boolean] = {
    if (record.isEmpty) {
      ZIO.logInfo(s"Ignoring an empty record for sourceA") zipRight ZIO.succeed(true)
    } else {
      val result = record.get match {
        case SourceRecord(_, None) =>
          for {
            _ <- workBuffer.offer(record.get)
            _ <- ZIO.logInfo(s"Offering a done record for sourceA and pausing consumption")
            cr <- controlBuffer.take
          } yield cr
        case SourceRecord(_, Some(_)) =>
          for {
            or <- workBuffer.offer(record.get)
            _ <- ZIO.logInfo(s"Offering an ongoing record for sourceA")
          } yield or
      }
      result
    }
  }
}

object SourceAExecutor {

  def live(): ZLayer[SourceAClient, Nothing, SourceAExecutor] = {
    ZLayer.fromFunction(SourceAExecutorImpl(_))
  }

}


