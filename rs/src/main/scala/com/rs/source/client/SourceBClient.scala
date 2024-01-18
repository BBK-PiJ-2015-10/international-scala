package com.rs.source.client

import com.rs.source.client.ApiEntities._
import zio.{ZIO, ZLayer}
import zio.http.{Body, Client, Method, URL}
import com.rs.parser.Parser

trait SourceBClient {
  def fetchRecord(): ZIO[Client, Throwable, Option[SourceRecord]]

}

case class SourceBClientImpl(urlString: String, client: Client) extends SourceBClient {

  val url = URL.decode(urlString).toOption.get

  override def fetchRecord(): ZIO[Client, Throwable, Option[SourceRecord]] = for {
    client <- ZIO.service[Client]
    response <- client.url(url).request(Method.GET, "/source/b", Body.empty)
    xmlResponse <- response.body.asString
    record = Parser.xmlStringToSourceRecord(xmlResponse)
  } yield record

}

object SourceBClient {

  def live(sourceUrl: String): ZLayer[Client, Nothing, SourceBClient] =
    ZLayer.fromFunction(SourceBClientImpl(sourceUrl,_))

}
