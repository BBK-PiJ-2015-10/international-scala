package com.hs.code.challenge.invitationservice.service.external.client

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

object ApiEntities {

  case class Partners(partners: List[Partner])

  case class Partner(firstName: String, lastName: String, email: String, country: String, availableDates: List[String])

  implicit val encoderPartner: JsonEncoder[Partner] = DeriveJsonEncoder.gen
  implicit val decoderPartner: JsonDecoder[Partner] = DeriveJsonDecoder.gen

  implicit val encoderPartners: JsonEncoder[Partners] = DeriveJsonEncoder.gen
  implicit val decoderPartners: JsonDecoder[Partners] = DeriveJsonDecoder.gen

  case class Country(attendeeCount: Int, attendees: List[String], name: String, startDate: String)

  implicit val encoderCountry: JsonEncoder[Country] = DeriveJsonEncoder.gen
  implicit val decoderCountry: JsonDecoder[Country] = DeriveJsonDecoder.gen

}
