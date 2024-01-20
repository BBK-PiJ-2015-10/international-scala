package com.rs.service

import com.rs.sink.client.ApiEntities.SubmissionRecord
import com.rs.source.client.ApiEntities.SourceRecord
import zio.Queue

trait Coordinator {

}


case class CoordinatorImpl(recordProcessor: RecordProcessor,sourceAExecutor: SourceAExecutor,sourceBExecutor: SourceBExecutor){


  def processRecords(inputChannel: Queue[SourceRecord], outputChannel: Queue[SubmissionRecord],controlBufferA: Queue[Boolean],controlBufferB: Queue[Boolean) = for {
    sourceA <- sourceAExecutor.fetchRecords(inputChannel,controlBufferA).fork
    sourceB <- sourceBExecutor.fetchRecords(inputChannel,controlBufferB).fork
    processor <- recordProcessor.processRecords(inputChannel, outputChannel).fork
    // join on the processor and if true, then release the other sources

  } yield()

}
