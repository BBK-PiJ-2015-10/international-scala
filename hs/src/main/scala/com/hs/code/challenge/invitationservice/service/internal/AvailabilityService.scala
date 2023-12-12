package com.hs.code.challenge.invitationservice.service.internal

import com.hs.code.challenge.invitationservice.dto.Dto
import com.hs.code.challenge.invitationservice.dto.Dto._
import com.hs.code.challenge.invitationservice.mapper.Mappers
import com.hs.code.challenge.invitationservice.service.external.client.{ApiEntities, PartnerServiceWebClient, ResultServiceWebClient}
import com.hs.code.challenge.invitationservice.service.external.client.ApiEntities.{Partner, Partners}
import zio.ZIO
import zio.http.Client

import java.time.LocalDate
import scala.collection.mutable
import scala.collection.mutable.Map
//import scala.collection.mutable.List

trait AvailabilityService {

  //def processAvailability(partners: List[Partner]): Unit

}

case class AvailabilityServiceImpl() {

  def processAvailability(): ZIO[Client with ResultServiceWebClient with PartnerServiceWebClient, Throwable, String] = {
    for {
      _  <- ZIO.logInfo("Start fetching partner data")
      partnerServiceWebClient <- ZIO.service[PartnerServiceWebClient]
      partners   <- partnerServiceWebClient.fetchPartnersAvailability()
      countriesAvailability = processPartners(partners.partners)
      resultServiceWebClient <- ZIO.service[ResultServiceWebClient]
      resultResponse <- resultServiceWebClient.submitResults(countriesAvailability)
      _    <- ZIO.logInfo("Received response")
    } yield resultResponse
  }

  private def processPartners(partners: List[Partner]): List[ApiEntities.Country] = {

    val countryDatesParticipant: Map[Country, Map[LocalDate, List[Email]]] = scala.collection.mutable.Map()
    partners.map(p => loadCountryDatesParticipants(p, countryDatesParticipant))

    val availabilityByCountry: Map[Country, Map[LocalDate, List[Email]]] = scala.collection.mutable.Map()
    countryDatesParticipant.keys.toList.foreach(c => availabilityByCountry.put(c, scala.collection.mutable.Map()))

    countryDatesParticipant.keys.foreach(country => {
      val inputMap = countryDatesParticipant.get(country).get
      val outputMap = availabilityByCountry.get(country).get
      loadCommonAvailability(inputMap, outputMap)
    })

    val countriesAvailability = countryDatesParticipant.groupMap(c => c._1)(c => extractMaxAvailability(c._2))
      .map(m => (m._1, m._2.head))

    Mappers.toApiCountry(countriesAvailability)

  }

  private def loadCountryDatesParticipants(partner: Partner, countryDatesParticipant: Map[Country, Map[LocalDate, List[Email]]]) = {
    val country: Country = Country(partner.country)
    val email: Email = Email(partner.email)
    val dates: List[LocalDate] = partner.availableDates.map(d => LocalDate.parse(d))
    if (countryDatesParticipant.contains(country)) {
      val existingDates: Map[LocalDate, List[Email]] = countryDatesParticipant.get(country).get
      dates.map(date => {
        if (existingDates.contains(date)) {
          // update existing list and update DatesMap
          val existingEmails: List[Email] = existingDates.get(date).get
          val updatedEmails = existingEmails ++ List(email)
          existingDates.put(date, updatedEmails)
        } else {
          // update DatesMap
          val updatedEmails = List(email)
          existingDates.put(date, updatedEmails)
        }
      }
      )
    } else {
      // create LocalDate,List[Email] map for country
      val datesEmails: Map[LocalDate, List[Email]] = dates.groupMap(d => d)(_ => email).to(collection.mutable.Map)
      countryDatesParticipant.put(country, datesEmails)
    }
  }

  private def loadCommonAvailability(input: Map[LocalDate, List[Email]], output:Map[LocalDate, List[Email]]): Unit = {
    for (availableDate <- input.keys.toList.sorted){
      val nextDay = availableDate.plusDays(1)
      if (input.contains(nextDay)){
        // grab participants of next day
        val nextDayParticipants = input.get(nextDay).get
        val currentDayParticipants = input.get(availableDate).get
        val participantsOnBothDates: List[Email] = currentDayParticipants.filter(p => nextDayParticipants.contains(p))
        output.put(availableDate,participantsOnBothDates)
      }
    }
  }

  private def extractMaxAvailability(datesMap: Map[LocalDate, List[Email]]): Option[(LocalDate, List[Email])] = {
    val list: List[(LocalDate, List[Email])] = datesMap.keys.map(d => (d,datesMap.get(d).get)).toList
    implicit val orderingByListSizeAndDate: Ordering[(LocalDate,List[Email])] = Ordering.by {
      tuple:(LocalDate,List[Email]) => (tuple._2.size,tuple._1)
    }
    list.sorted.headOption
  }

}


