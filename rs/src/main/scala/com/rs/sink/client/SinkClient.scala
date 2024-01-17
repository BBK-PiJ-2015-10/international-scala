package com.rs.sink.client

import com.rs.sink.client.ApiEntities.{SinkResponse, SubmissionRecord}
import zio._
import zio.http.{Body, Client, Method, URL}
import com.rs.parser.Parser
import zio.json.EncoderOps

trait SinkClient {
  def submitRecord(submissionRecord: SubmissionRecord): ZIO[Client, Throwable, Option[SinkResponse]]

}

case class SinkClientImpl(sinkUrl: String) extends SinkClient {

  val url = URL.decode(sinkUrl).toOption.get

  override def submitRecord(submissionRecord: SubmissionRecord): ZIO[Client, Throwable, Option[SinkResponse]] = for {
    client <- ZIO.service[Client]
    jsonRequest = submissionRecord.toJson
    body = Body.fromString(jsonRequest)
    response <- client.url(url).request(Method.POST, "/sink/a", body)
    jsonResponse <- response.body.asString
  } yield Parser.jsonStringToSinkResponse(jsonResponse)

}

object SinkClient {

  def live(url: String): ZLayer[Any, Throwable, SinkClientImpl] = ZLayer.fromZIO(
    ZIO.attempt(SinkClientImpl(url))
  )
}


