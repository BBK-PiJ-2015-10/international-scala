package com.rs.service

import com.rs.sink.client.ApiEntities.{SubmissionRecord, encoderSinkResponse}
import com.rs.source.client.ApiEntities.SourceRecord
import zio.{Duration, Queue, ZIO, ZLayer}

trait Coordinator {
  def processRecords(inputChannel: Queue[SourceRecord], outputChannel: Queue[SubmissionRecord], controlBufferA: Queue[Boolean], controlBufferB: Queue[Boolean]): ZIO[Any, Throwable, Boolean]
}


case class CoordinatorImpl(recordProcessor: RecordProcessor, sourceAExecutor: SourceAExecutor, sourceBExecutor: SourceBExecutor, sinkExecutor: SinkExecutor) extends Coordinator {

  override def processRecords(inputChannel: Queue[SourceRecord], outputChannel: Queue[SubmissionRecord], controlBufferA: Queue[Boolean], controlBufferB: Queue[Boolean]) = for {
    //TODO: forever seems to never go beyond sourceAExecutor, try to fork
    _ <- sourceAExecutor.fetchRecords(inputChannel, controlBufferA).forever
    _ <-  sourceBExecutor.fetchRecords(inputChannel, controlBufferB).forever
    _ <-recordProcessor.processRecords(inputChannel, outputChannel).forever
    _ <- sinkExecutor.submitRecords(outputChannel).forever
    _ <- ZIO.logInfo("napping for 10 seconds")
    _ <- ZIO.sleep(Duration.fromMillis(1000))
    _ <- ZIO.logInfo("done napping for 10 seconds")
    //sourceA <- sourceAExecutor.fetchRecords(inputChannel, controlBufferA).fork
    //sourceB <- sourceBExecutor.fetchRecords(inputChannel, controlBufferB).fork
    //processor <- recordProcessor.processRecords(inputChannel, outputChannel).fork
    //sink <- sinkExecutor.submitRecords(outputChannel).fork
    _ <- ZIO.logInfo("GOOD BYE")
  } yield true

}


object Coordinator {

  def live(): ZLayer[RecordProcessor with SourceAExecutor with SourceBExecutor with SinkExecutor, Nothing, CoordinatorImpl] =
    ZLayer.fromFunction(CoordinatorImpl(_, _, _, _))

}