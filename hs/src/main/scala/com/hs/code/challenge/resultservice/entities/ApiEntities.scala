package com.hs.code.challenge.resultservice.entities

import zio.json._

object ApiEntities {

  case class Country(attendeeCount: Int, attendees: List[String], name: String, startDate: Option[String])

  implicit val encoderCountry: JsonEncoder[Country] = DeriveJsonEncoder.gen
  implicit val decoderCountry: JsonDecoder[Country] = DeriveJsonDecoder.gen
  case class Recommendation(productId: Int, recommendationId: Int, author: String, rate: Int, content: String, serviceAddress: String)

  implicit val encoderRecommendation: JsonEncoder[Recommendation] = DeriveJsonEncoder.gen
  implicit val decoderRecommendation: JsonDecoder[Recommendation] = DeriveJsonDecoder.gen

  case class ErrorMessage(message: String)

  implicit val encoderErrorMessage: JsonEncoder[ErrorMessage] = DeriveJsonEncoder.gen
  implicit val decoderErrorMessage: JsonDecoder[ErrorMessage] = DeriveJsonDecoder.gen

}
