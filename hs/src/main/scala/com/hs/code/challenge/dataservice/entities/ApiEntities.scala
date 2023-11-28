package com.hs.code.challenge.dataservice.entities

import zio.json._

object ApiEntities {

  case class Recommendation(productId: Int, recommendationId: Int, author: String, rate: Int, content: String, serviceAddress: String)

  implicit val encoderRecommendation: JsonEncoder[Recommendation] = DeriveJsonEncoder.gen
  implicit val decoderRecommendation: JsonDecoder[Recommendation] = DeriveJsonDecoder.gen

  case class ErrorMessage(message: String)

  implicit val encoderErrorMessage: JsonEncoder[ErrorMessage] = DeriveJsonEncoder.gen
  implicit val decoderErrorMessage: JsonDecoder[ErrorMessage] = DeriveJsonDecoder.gen

}
