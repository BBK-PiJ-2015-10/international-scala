package com.rs.service.external.source.client

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

object ApiEntities {

  case class SourceRecord(status: String,id:Option[String])

  implicit val encoderSourceARecord: JsonEncoder[SourceRecord] = DeriveJsonEncoder.gen
  implicit val decoderSourceARecord: JsonDecoder[SourceRecord] = DeriveJsonDecoder.gen

}
