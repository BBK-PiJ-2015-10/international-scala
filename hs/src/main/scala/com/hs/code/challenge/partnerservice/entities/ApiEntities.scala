package com.hs.code.challenge.partnerservice.entities

import java.time.LocalDate
import zio.json.{DeriveJsonCodec, JsonCodec}

object ApiEntities {
  case class Partner(firstName: String, lastName: String, email: String, country: String, availableDates: List[LocalDate])

  implicit val partnerJsonCodec: JsonCodec[Partner] = DeriveJsonCodec.gen

}
