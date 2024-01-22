package com.rs.service

import com.rs.sink.client.ApiEntities.SubmissionRecord
import com.rs.source.client.ApiEntities.SourceRecord
import zio.{Queue, ZIO, ZLayer}

trait Coordinator {
  def processRecords(inputChannel: Queue[SourceRecord], outputChannel: Queue[SubmissionRecord], controlBufferA: Queue[Boolean], controlBufferB: Queue[Boolean]): ZIO[Any, Throwable, Unit]
}


case class CoordinatorImpl(recordProcessor: RecordProcessor, sourceAExecutor: SourceAExecutor, sourceBExecutor: SourceBExecutor, sinkExecutor: SinkExecutor) extends Coordinator {


  override def processRecords(inputChannel: Queue[SourceRecord], outputChannel: Queue[SubmissionRecord], controlBufferA: Queue[Boolean], controlBufferB: Queue[Boolean]) = for {
    sourceA <- sourceAExecutor.fetchRecords(inputChannel, controlBufferA).fork
    sourceB <- sourceBExecutor.fetchRecords(inputChannel, controlBufferB).fork
    processor <- recordProcessor.processRecords(inputChannel, outputChannel).fork
    sink <- sinkExecutor.submitRecords(outputChannel).fork
    _ <- ZIO.attempt(true)
  } yield ()

}


object Coordinator {

  def layer(): ZLayer[RecordProcessor with SourceAExecutor with SourceBExecutor with SinkExecutor, Nothing, CoordinatorImpl] =
    ZLayer.fromFunction(CoordinatorImpl(_, _, _, _))

}