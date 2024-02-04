package com.rs.service

import com.rs.sink.client.ApiEntities.{SubmissionRecord}
import com.rs.source.client.ApiEntities.SourceRecord
import zio.{Duration, Queue, ZIO, ZLayer}

trait Coordinator {
  def processRecords(inputChannel: Queue[SourceRecord], outputChannel: Queue[SubmissionRecord], controlBufferA: Queue[Boolean], controlBufferB: Queue[Boolean]): ZIO[Any, Throwable, Boolean]
}


case class CoordinatorImpl(recordProcessor: RecordProcessor, sourceAExecutor: SourceAExecutor, sourceBExecutor: SourceBExecutor, sinkExecutor: SinkExecutor) extends Coordinator {

  override def processRecords(inputChannel: Queue[SourceRecord], outputChannel: Queue[SubmissionRecord], controlBufferA: Queue[Boolean], controlBufferB: Queue[Boolean]) = for {
    f1 <- launchExecutorA(inputChannel, controlBufferA).fork
    f2 <- launchExecutorB(inputChannel, controlBufferB).fork
    f3 <- launchRecordProcessor(inputChannel, outputChannel).fork
    f4 <- lunchSinkExecutor(outputChannel).fork
    _ <- ZIO.logInfo("Joining on fibers")
    j1 <- f1.join
    j2 <- f2.join
    j3 <- f3.join
    j4 <- f4.join
    _ <- ZIO.logInfo("done joining fibers")
    _ <- ZIO.logInfo("GOOD BYE")
  } yield true

  def launchExecutorA(inputChannel: Queue[SourceRecord], controlBufferA: Queue[Boolean]) = for {
    _ <- ZIO.sleep(Duration.fromMillis(1000))
    _ <- sourceAExecutor.fetchRecords(inputChannel, controlBufferA).forever
  } yield ()

  def launchExecutorB(inputChannel: Queue[SourceRecord], controlBufferB: Queue[Boolean]) = for {
    _ <- ZIO.sleep(Duration.fromMillis(1000))
    _ <- sourceBExecutor.fetchRecords(inputChannel, controlBufferB).forever
  } yield ()

  def launchRecordProcessor(inputChannel: Queue[SourceRecord], outputChannel: Queue[SubmissionRecord]) = for {
    _ <- recordProcessor.processRecords(inputChannel, outputChannel).forever
  } yield ()

  def lunchSinkExecutor(outputChannel: Queue[SubmissionRecord]) = for {
    _ <- sinkExecutor.submitRecords(outputChannel).forever
  } yield ()


}


object Coordinator {

  def live(): ZLayer[RecordProcessor with SourceAExecutor with SourceBExecutor with SinkExecutor, Nothing, CoordinatorImpl] =
    ZLayer.fromFunction(CoordinatorImpl(_, _, _, _))

}