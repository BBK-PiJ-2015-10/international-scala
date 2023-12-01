package com.hs.code.challenge.invitationservice.service.client

import zio.{ZIO, ZLayer}
import zio.http.{Body, Client, Method, URL}
import zio.json._
import com.hs.code.challenge.invitationservice.service.client.ApiEntities._

trait PartnerServiceWebClient {
  def fetchPartnersAvailability(): ZIO[Client, Throwable, Partners]

}

case class PartnerServiceWebClientImpl(urlString: String) extends PartnerServiceWebClient {

  val url = URL.decode(urlString).toOption.get

  def fetchPartnersAvailability(): ZIO[Client, Throwable, Partners] = for {
    client <- ZIO.service[Client]
    response <- client.url(url).request(Method.GET, "/partnerservice/partners", Body.empty)
    jsonResponse <- response.body.asString
    partners <- ZIO.fromEither(jsonResponse.fromJson[Partners])
      .mapError(e => new Throwable(s"$e"))
  } yield partners

}

object PartnerServiceWebClient {

  def live(partnerServiceUrl: String): ZLayer[Client, Throwable, PartnerServiceWebClientImpl] =
    ZLayer.fromZIO(ZIO.attempt(PartnerServiceWebClientImpl(partnerServiceUrl)))

}
