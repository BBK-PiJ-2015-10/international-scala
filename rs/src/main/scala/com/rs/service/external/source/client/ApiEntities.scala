package com.rs.service.external.source.client

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

object ApiEntities {

  case class SourceRecord(status: String,id:Option[String])

  implicit val encoderSourceRecord: JsonEncoder[SourceRecord] = DeriveJsonEncoder.gen
  implicit val decoderSourceRecord: JsonDecoder[SourceRecord] = DeriveJsonDecoder.gen

}
