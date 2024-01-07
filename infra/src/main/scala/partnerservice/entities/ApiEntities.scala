package partnerservice.entities

import zio.json._

object ApiEntities {

  case class Partners(partners: List[Partner])

  case class Partner(firstName: String, lastName: String, email: String, country: String, availableDates: List[String])

  implicit val encoderPartner: JsonEncoder[Partner] = DeriveJsonEncoder.gen
  implicit val decoderPartner: JsonDecoder[Partner] = DeriveJsonDecoder.gen

  implicit val encoderPartners: JsonEncoder[Partners] = DeriveJsonEncoder.gen
  implicit val decoderPartners: JsonDecoder[Partners] = DeriveJsonDecoder.gen

  case class ErrorMessage(message: String)

  implicit val encoderErrorMessage: JsonEncoder[ErrorMessage] = DeriveJsonEncoder.gen
  implicit val decoderErrorMessage: JsonDecoder[ErrorMessage] = DeriveJsonDecoder.gen


}
