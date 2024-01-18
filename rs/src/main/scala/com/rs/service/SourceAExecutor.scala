package com.rs.service

import com.rs.dto.Dto.Record
import com.rs.source.client.SourceAClient
import zio.{Queue, ZIO, ZLayer}

trait SourceAExecutor {

  def fetchRecords(workBuffer: Queue[Record],controlBuffer: Queue[Boolean]): ZIO[Any,Throwable,Boolean]

}

case class SourceAExecutorImpl(sourceAClient: SourceAClient) extends SourceAExecutor {

  override def fetchRecords(workBuffer: Queue[Record], controlBuffer: Queue[Boolean]): ZIO[Any, Throwable, Boolean] = {


    ZIO.attempt(true)
  }
}

object SourceAExecutor {

  def layer(): ZLayer[SourceAClient, Nothing, SourceAExecutor] = {
    ZLayer.fromFunction(SourceAExecutorImpl(_))
  }

}


