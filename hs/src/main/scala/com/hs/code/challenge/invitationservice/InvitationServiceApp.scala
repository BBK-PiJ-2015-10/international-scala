package com.hs.code.challenge.invitationservice

import com.hs.code.challenge.invitationservice.service.external.client.{PartnerServiceWebClient, ResultServiceWebClient}
import com.hs.code.challenge.invitationservice.service.internal.AvailabilityService
import zio._
import zio.http._
import zio.{Duration, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

object InvitationServiceApp extends ZIOAppDefault {

  val partnerServiceUrl = "http://localhost:8080"

  val resultServiceUrl = "http://localhost:9080"

  //http://localhost:9090
  override def run: ZIO[Any, Throwable, Unit] = (for {
    _ <- ZIO.logInfo("Starting")
    availabilityService   <-   ZIO.service[AvailabilityService]
    respResult   <- availabilityService.processAvailability()
    //psclient <- ZIO.service[PartnerServiceWebClient]
    //respPartner <- psclient.fetchPartnersAvailability()
    //_ <- ZIO.logInfo(s"Received response from parterService $respPartner")
    //resultClient <- ZIO.service[ResultServiceWebClient]
    //respResult <- resultClient.submitResults(List())
    _ <- ZIO.logInfo(s"Received response from parterService $respResult")
    - <- ZIO.sleep(Duration.fromSeconds(5))
    _ <- ZIO.logInfo("Closing")
  } yield ()).provide(
    Client.default,
    Scope.default,
    PartnerServiceWebClient.live(partnerServiceUrl),
    ResultServiceWebClient.live(resultServiceUrl),
    AvailabilityService.live()
  )
}
