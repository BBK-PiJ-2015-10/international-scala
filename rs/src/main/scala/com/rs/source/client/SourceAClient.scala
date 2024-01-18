package com.rs.source.client

import com.rs.source.client.ApiEntities._
import zio.{ZIO, ZLayer}
import zio.http.{Body, Client, Method, URL}
import com.rs.parser.Parser

trait SourceAClient {

  def fetchRecord(): ZIO[Any, Throwable, Option[SourceRecord]]

}

case class SourceAClientImpl(urlString: String, client: Client) extends SourceAClient {

  val url = URL.decode(urlString).toOption.get

  def fetchRecord(): ZIO[Any, Throwable, Option[SourceRecord]] = for {
    response <- client.url(url).request(Method.GET, "/source/a", Body.empty)
    jsonResponse <- response.body.asString
    record = Parser.jsonStringToSourceRecord(jsonResponse)
  } yield record

}

object SourceAClient {

  def live(sourceUrl: String): ZLayer[Client, Throwable, SourceAClient] =
    ZLayer.fromFunction(SourceAClientImpl(sourceUrl,_))

}

