package com.rs.sink.client

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}
object ApiEntities {

  case class SinkResponse(status: String)

  implicit val encoderSinkResponse: JsonEncoder[SinkResponse] = DeriveJsonEncoder.gen
  implicit val decoderSinkResponse: JsonDecoder[SinkResponse] = DeriveJsonDecoder.gen

  case class SubmissionRecord(kind: String,id: String)

  implicit val encoderSubmissionRecord: JsonEncoder[SubmissionRecord] = DeriveJsonEncoder.gen
  implicit val decoderSubmissionRecord: JsonDecoder[SubmissionRecord] = DeriveJsonDecoder.gen

}
