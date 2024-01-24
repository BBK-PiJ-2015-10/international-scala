package com.rs.service

import com.rs.sink.client.ApiEntities.SubmissionRecord
import com.rs.sink.client.SinkClient
import zio._

trait SinkExecutor {
  def submitRecords(outputChannel: Queue[SubmissionRecord]): ZIO[Any, Throwable, Boolean]
}

case class SinkExecutorImpl(sinkClient: SinkClient) extends SinkExecutor {
  override def submitRecords(outputChannel: Queue[SubmissionRecord]): ZIO[Any, Throwable, Boolean] = for {
    submissionRecord <- outputChannel.take
    _ <- ZIO.logInfo(s"Submitting response $submissionRecord")
    response <- sinkClient.submitRecord(submissionRecord)
    _ <- ZIO.logInfo(s"Received submission response")
  } yield response.isDefined
}


object SinkExecutor {

  def live(): ZLayer[SinkClient, Throwable, SinkExecutor] =
    ZLayer.fromFunction(SinkExecutorImpl(_))

}
