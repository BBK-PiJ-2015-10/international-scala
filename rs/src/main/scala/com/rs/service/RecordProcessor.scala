package com.rs.service

import .Record
import zio.{Queue, ZIO}

trait RecordProcessor {

  def processRecords(inputChannel: Queue[Record], outputChannel: Queue[Record]): ZIO[Any, Throwable, Boolean]

}


case class RecordProcessorImpl() extends RecordProcessor {


  override def processRecords(inputChannel: Queue[Record], outputChannel: Queue[Record]): ZIO[Any, Throwable, Boolean] = {
    for {
      _ <- ZIO.logInfo(s"Starting processing")
      inputRecord <- inputChannel.take

    } yield(true)
  }
}
