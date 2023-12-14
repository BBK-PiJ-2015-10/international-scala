package com.hs.code.challenge.invitationservice.service.internal

import com.hs.code.challenge.invitationservice.domain.InvitationCalculator
import com.hs.code.challenge.invitationservice.service.external.client.{PartnerServiceWebClient, ResultServiceWebClient}
import zio.{ZIO, ZLayer}
import zio.http.Client

trait AvailabilityService {

  def processAvailability(): ZIO[Client with ResultServiceWebClient with PartnerServiceWebClient, Throwable, String]

}

case class AvailabilityServiceImpl() extends AvailabilityService {

  def processAvailability(): ZIO[Client with ResultServiceWebClient with PartnerServiceWebClient, Throwable, String] = {
    for {
      _ <- ZIO.logInfo("Start fetching partner data")
      partnerServiceWebClient <- ZIO.service[PartnerServiceWebClient]
      partners <- partnerServiceWebClient.fetchPartnersAvailability()
      countriesAvailability = InvitationCalculator.processPartners(partners.partners)
      resultServiceWebClient <- ZIO.service[ResultServiceWebClient]
      resultResponse <- resultServiceWebClient.submitResults(countriesAvailability)
      _ <- ZIO.logInfo("Received response")
    } yield resultResponse
  }

}


object AvailabilityService {

  def live(): ZLayer[Any, Throwable, AvailabilityService] =
    ZLayer.fromZIO(ZIO.from(AvailabilityServiceImpl()))
}

