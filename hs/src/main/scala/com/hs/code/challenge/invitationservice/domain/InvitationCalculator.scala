package com.hs.code.challenge.invitationservice.domain


import com.hs.code.challenge.invitationservice.dto.Dto._
import com.hs.code.challenge.invitationservice.mapper.Mappers
import com.hs.code.challenge.invitationservice.service.external.client.{ApiEntities, PartnerServiceWebClient, ResultServiceWebClient}
import com.hs.code.challenge.invitationservice.service.external.client.ApiEntities.Partner

import java.time.LocalDate
import scala.collection.mutable.Map

object InvitationCalculator {

  def processPartners(partners: List[Partner]): List[ApiEntities.Country] = {

    val countryDatesParticipant: Map[Country, Map[LocalDate, List[Email]]] = scala.collection.mutable.Map()
    partners.map(p => loadCountryDatesParticipants(p, countryDatesParticipant))

    val availabilityByCountry: Map[Country, Map[LocalDate, List[Email]]] = scala.collection.mutable.Map()
    countryDatesParticipant.keys.toList.foreach(c => availabilityByCountry.put(c, scala.collection.mutable.Map()))

    countryDatesParticipant.keys.foreach(country => {
      val inputMap = countryDatesParticipant.get(country).get
      val outputMap = availabilityByCountry.get(country).get
      loadCommonAvailability(inputMap, outputMap)
    })

    val countriesAvailability = countryDatesParticipant.groupMap(c => c._1)(c => extractMaxAvailability(availabilityByCountry.get(c._1)))
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

  private def loadCommonAvailability(input: Map[LocalDate, List[Email]], output: Map[LocalDate, List[Email]])= {

    for (availableDate <- input.keys.toList.sorted) {
      val nextDay = availableDate.plusDays(1)
      if (input.contains(nextDay)) {
        // grab participants of next day
        val nextDayParticipants = input.get(nextDay).get
        val currentDayParticipants = input.get(availableDate).get
        val participantsOnBothDates: List[Email] = currentDayParticipants.filter(p => nextDayParticipants.contains(p))
        if (participantsOnBothDates.nonEmpty) {
          output.put(availableDate, participantsOnBothDates)
        }
      }
    }
  }

  private def extractMaxAvailability(datesMapOpt: Option[Map[LocalDate, List[Email]]]): Option[(LocalDate, List[Email])] = {
    if (datesMapOpt.isEmpty | datesMapOpt.get.isEmpty) {
      None
    }
    else {
      val maxSize = datesMapOpt.get.values.map(_.size).max
      val datesMap = datesMapOpt.get
      val maxParticipantDates = datesMap.keys.filter(k => datesMap.get(k).get.size >= maxSize).toList
      val list: List[(LocalDate, List[Email])] = maxParticipantDates.map(d => (d, datesMap.get(d).get))
      implicit val orderingByListSizeAndDate: Ordering[(LocalDate, List[Email])] = Ordering.by {
        tuple: (LocalDate, List[Email]) => tuple._1
      }
      list.sorted.headOption
    }
  }

}
