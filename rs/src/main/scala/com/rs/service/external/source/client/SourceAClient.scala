package com.rs.service.external.source.client

import zio.ZIO

import ApiEntities._
import zio.http.{Body, Client, Method, URL}
import zio.json._
import zio.{ZIO, ZLayer}

trait SourceAClient {

  def fetchRecords(): ZIO[Client, Throwable, Option[SourceRecord]]

}

case class SourceAClientImpl(urlString: String) extends SourceAClient {

  val url = URL.decode(urlString).toOption.get


  override def fetchRecords() = for {
    client <- ZIO.service[Client]
    response <- client.url(url).request(Method.GET, "/source/a", Body.empty)
    jsonResponse <- response.body.asString
    response = jsonResponse.fromJson[SourceRecord]
    maybeSourceARecord = response match {
      case Left(_) => None
      case Right(r) => Some(r)
    }
  } yield maybeSourceARecord

}

