package com.hs.code.challenge.invitationservice.service.internal

import com.hs.code.challenge.invitationservice.dto.Dto
import com.hs.code.challenge.invitationservice.dto.Dto._
import com.hs.code.challenge.invitationservice.service.external.client.ApiEntities.{Partner, Partners}

import java.time.LocalDate
import scala.collection.mutable
import scala.collection.mutable.Map
//import scala.collection.mutable.List

trait AvailabilityService {

  def processAvailability(partners: List[Partner]): Unit


}

case class AvailabilityServiceImpl() extends AvailabilityService {

  def loadCountryDatesParticipants(partner: Partner, countryDatesParticipant: Map[Country, Map[LocalDate, List[Email]]]) = {
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


  override def processAvailability(partners: List[Partner]): Unit = {

    val countryDatesParticipant: Map[Country, Map[LocalDate, List[Email]]] = scala.collection.mutable.Map()
    partners.map(p => loadCountryDatesParticipants(p, countryDatesParticipant))

    // TODO: calculate , maxAvailableDate







    //    val countryDates: Map[Country, Set[LocalDate]] = partners.groupMap(p => Country(p.country))(p => p.availableDates.map(d => LocalDate.parse(d)))
    //      .map(m => (m._1,m._2.flatten.toSet))


    val other = partners.map(_.availableDates).flatten.map(d => LocalDate.parse(d)).toSet


    //val availableDates = partners.map(_.availableDates).flatMap(d => LocalDate.parse(d)).toSet.flatten.groupBy(d => d).map(d => (d._1,d._2.head))

    //val loco = LocalDate.parse("dada")


  }
}
