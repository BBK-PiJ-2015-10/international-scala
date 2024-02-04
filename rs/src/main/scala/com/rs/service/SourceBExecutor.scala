package com.rs.service

import com.rs.source.client.ApiEntities.SourceRecord
import com.rs.source.client.SourceBClient
import zio.{Duration, Queue, ZIO, ZLayer}

trait SourceBExecutor {

  def fetchRecords(workBuffer: Queue[SourceRecord], controlBuffer: Queue[Boolean]): ZIO[Any, Throwable, Boolean]

}

case class SourceBExecutorImpl(sourceBClient: SourceBClient) extends SourceBExecutor {

  override def fetchRecords(workBuffer: Queue[SourceRecord], controlBuffer: Queue[Boolean]): ZIO[Any, Throwable, Boolean] = (for {
    - <- ZIO.logInfo(s"consuming sourceB message")
    sourceARecord <- sourceBClient.fetchRecord()
    result <- processRecord(workBuffer, controlBuffer, sourceARecord)
    _ <- ZIO.logInfo("consumed sourceB message")
  } yield result).retryN(10)

  def processRecord(workBuffer: Queue[SourceRecord], controlBuffer: Queue[Boolean], record: Option[SourceRecord]): ZIO[Any, Nothing, Boolean] = {
    if (record.isEmpty) {
      ZIO.logInfo(s"Executor SourceB  Ignoring an empty record from sourceB") zipRight ZIO.succeed(true)
    } else {
      val result = record.get match {
        case SourceRecord(_, None) =>
          for {
            _ <- workBuffer.offer(record.get)
            _ <- ZIO.logInfo(s"Executor SourceB Offering a done record :${record.get} from sourceB and pausing consumption")
            cr <- controlBuffer.take
          } yield cr
        case SourceRecord(_, Some(_)) =>
          for {
            or <- workBuffer.offer(record.get)
            _ <- ZIO.logInfo(s"Executor SourceB offering an ongoing record ${record.get} from sourceB")
          } yield or
      }
      result
    }
  }
}

object SourceBExecutor {

  def live(): ZLayer[SourceBClient, Nothing, SourceBExecutor] = {
    ZLayer.fromFunction(SourceBExecutorImpl(_))
  }

}