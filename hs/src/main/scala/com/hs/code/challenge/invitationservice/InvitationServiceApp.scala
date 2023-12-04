package com.hs.code.challenge.invitationservice

import com.hs.code.challenge.invitationservice.service.client.ApiEntities.Partner
import com.hs.code.challenge.invitationservice.service.client.{PartnerServiceWebClient, ResultServiceWebClient}
import zio._
import zio.http._
import zio.{Duration, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object InvitationServiceApp extends ZIOAppDefault {

  val partnerServiceUrl = "http://localhost:8080"

  val resultServiceUrl = "http://localhost/9080"

  //http://localhost:9090
  override def run: ZIO[Any, Throwable, Unit] = (for {
    _ <- ZIO.logInfo("Starting")
    psclient <- ZIO.service[PartnerServiceWebClient]
    resp <- psclient.fetchPartnersAvailability()
    _ <- ZIO.logInfo(s"Received response $resp")
    - <- ZIO.sleep(Duration.fromSeconds(5))
    _ <- ZIO.logInfo("Closing")
  } yield ()).provide(
    Client.default,
    Scope.default,
    PartnerServiceWebClient.live(partnerServiceUrl),
    ResultServiceWebClient.live(resultServiceUrl)
  )
}
