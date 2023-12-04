package com.hs.code.challenge.invitationservice.service.client

import com.hs.code.challenge.resultservice.entities.ApiEntities.Country
import zio.{ZIO, ZLayer}
import zio.http._
import zio.json._

trait ResultServiceWebClient {

  def submitResults(countries: List[Country]): ZIO[Client,Throwable,String]

}

case class ResultServiceWebClientImpl(serviceUrl: String) extends ResultServiceWebClient {

  val url = URL.decode(serviceUrl).toOption.get

  override def submitResults(countries: List[Country]): ZIO[Client, Throwable,String] = for {
    client <- ZIO.service[Client]
    _  <- ZIO.logInfo(s"Submitted countries availability to result service")
    countriesJsonString = countries.toJson
    body = Body.fromString(s"$countriesJsonString")
    response   <- client.url(url).request(Method.POST,"/candidateTest/v3/problem/result",body)
    jsonResponseString <- response.body.asString
    _  <- ZIO.logInfo(s"Received result response $jsonResponseString")
  } yield jsonResponseString

}

object ResultServiceWebClient {

  def live(serviceUrl: String): ZLayer[Any, Throwable, ResultServiceWebClient] =
    ZLayer.fromZIO(ZIO.from(ResultServiceWebClientImpl(serviceUrl)))

}
