package com.hs.code.challenge.invitationservice.mapper

import com.hs.code.challenge.invitationservice.dto.Dto.{Country, Email}
import com.hs.code.challenge.invitationservice.service.external.client.ApiEntities

import java.time.LocalDate

object Mappers {
  def toApiCountry(countriesAvailability: Map[Country, Option[(LocalDate, List[Email])]]) : List[ApiEntities.Country] = {
    countriesAvailability.keySet.map(country => {
      val countryAvailability = countriesAvailability.get(country).get
      countryAvailability.map(ca => ApiEntities.Country(
        ca._2.size,ca._2.map(_.email),country.country,Some(ca._1.toString)
      )).getOrElse(ApiEntities.Country(0,List(),country.country,None))
    }).toList
  }

}
