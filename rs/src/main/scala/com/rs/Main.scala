package com.rs

import com.rs.service.{Coordinator, RecordProcessor, SinkExecutor, SourceAExecutor, SourceBExecutor}
import com.rs.sink.client.ApiEntities.SubmissionRecord
import com.rs.sink.client.SinkClient
import com.rs.source.client.ApiEntities.SourceRecord
import com.rs.source.client._
import zio.Console.printLine
import zio._
import zio.http._

object Main extends ZIOAppDefault {

  val sourceUrl = "http://localhost:7299"


  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] = (for {
    _ <- ZIO.logInfo("starting")
    coordinator <- ZIO.service[Coordinator]
    controlABuffer <- Queue.bounded[Boolean](1)
    controlBBuffer <- Queue.bounded[Boolean](1)
    inputChannel <- Queue.unbounded[SourceRecord]
    ouputChannel <- Queue.unbounded[SubmissionRecord]
    _  <- coordinator.processRecords(inputChannel,ouputChannel,controlABuffer,controlBBuffer)
    _  <- ZIO.sleep(Duration.fromMillis(100))
    _ <- ZIO.logInfo("done")
  } yield ()).provide(
     Client.default,
    SourceAClient.live(sourceUrl),
    SourceBClient.live(sourceUrl),
    SinkClient.live(sourceUrl),
    SourceAExecutor.live(),
    SourceBExecutor.live(),
    SinkExecutor.live(),
    RecordProcessor.live(),
    Coordinator.live(),

  )
    printLine("Welcome to your first ZIO app!")
}