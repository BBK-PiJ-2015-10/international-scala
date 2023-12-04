package com.hs.code.challenge.invitationservice.service.client

import com.hs.code.challenge.resultservice.entities.ApiEntities.Country
import zio.{ZIO, ZLayer}
import zio.http._
import zio.json._

trait ResultServiceWebClient {

  def submitResults(countries: List[Country]): ZIO[Client,Throwable,List[Country]]

}

case class ResultServiceWebClientImpl(serviceUrl: String) extends ResultServiceWebClient {

  val url = URL.decode(serviceUrl).toOption.get

  override def submitResults(countries: List[Country]): ZIO[Client, Throwable, List[Country]] = for {
    client <- ZIO.service[Client]
    countriesJsonString = countries.toJson
    body = Body.fromString(s"$countriesJsonString")
    response   <- client.url(url).request(Method.POST,"/candidateTest/v3/problem/result",body)
    jsonResponseString <- response.body.asString
    resultResponse <- ZIO.fromEither(jsonResponseString.fromJson[List[Country]])
      .mapError(e => new Throwable(e))
  } yield resultResponse

}

object ResultServiceWebClient {

  def live(serviceUrl: String): ZLayer[Any, Throwable, ResultServiceWebClient] =
    ZLayer.fromZIO(ZIO.from(ResultServiceWebClientImpl(serviceUrl)))

}
