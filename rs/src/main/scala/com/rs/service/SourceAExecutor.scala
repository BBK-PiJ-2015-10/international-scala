package com.rs.service

import com.rs.source.client.ApiEntities.SourceRecord
import com.rs.source.client.SourceAClient
import zio.{Duration, Queue, ZIO, ZLayer}

trait SourceAExecutor {
  def fetchRecords(workBuffer: Queue[SourceRecord], controlBuffer: Queue[Boolean]): ZIO[Any, Throwable, Boolean]

}

case class SourceAExecutorImpl(sourceAClient: SourceAClient) extends SourceAExecutor {

  override def fetchRecords(workBuffer: Queue[SourceRecord], controlBuffer: Queue[Boolean]): ZIO[Any, Throwable, Boolean] = (for {
    - <- ZIO.logInfo(s"consuming sourceA message")
    sourceARecord <- sourceAClient.fetchRecord()
    result <- processRecord(workBuffer, controlBuffer, sourceARecord)
    _ <- ZIO.logInfo("consumed sourceA message")
  } yield result).retryN(10)

  def processRecord(workBuffer: Queue[SourceRecord], controlBuffer: Queue[Boolean], record: Option[SourceRecord]): ZIO[Any, Nothing, Boolean] = {
    if (record.isEmpty) {
      ZIO.logInfo(s"Executor ignoring an empty record from sourceA") zipRight ZIO.succeed(true)
    } else {
      val result = record.get match {
        case SourceRecord(_, None) =>
          for {
            _ <- workBuffer.offer(record.get)
            _ <- ZIO.logInfo(s"Executor offering a done record : ${record.get} from sourceA and pausing consumption")
            cr <- controlBuffer.take
          } yield cr
        case SourceRecord(_, Some(_)) =>
          for {
            or <- workBuffer.offer(record.get)
            _ <- ZIO.logInfo(s"Executor offering an ongoing record : ${record.get} from sourceA")
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


