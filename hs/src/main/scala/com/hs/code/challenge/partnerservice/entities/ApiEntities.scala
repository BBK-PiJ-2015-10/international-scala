package com.hs.code.challenge.partnerservice.entities

import java.time.LocalDate
import zio.json.{DeriveJsonCodec, JsonCodec}

object ApiEntities {

  case class Partners(partners: List[Partner])
  case class Partner(firstName: String, lastName: String, email: String, country: String, availableDates: List[LocalDate])

  case class ErrorMessage(message: String)


  implicit val partnerJsonCodec: JsonCodec[Partner] = DeriveJsonCodec.gen

  implicit val errorMessageJsonCodec: JsonCodec[ErrorMessage] = DeriveJsonCodec.gen

}
