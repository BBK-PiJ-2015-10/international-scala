package com.hs.code.challenge.invitationservice.service.client

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

object ApiEntities {

  case class Partners(partners: List[Partner])

  case class Partner(firstName: String, lastName: String, email: String, country: String, availableDates: List[String])

  implicit val encoderPartner: JsonEncoder[Partner] = DeriveJsonEncoder.gen
  implicit val decoderPartner: JsonDecoder[Partner] = DeriveJsonDecoder.gen

  implicit val encoderPartners: JsonEncoder[Partners] = DeriveJsonEncoder.gen
  implicit val decoderPartners: JsonDecoder[Partners] = DeriveJsonDecoder.gen

}
