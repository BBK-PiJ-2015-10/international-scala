package com.rs.service.external.source.client

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

object ApiEntities {

  case class SourceARecord(status: String,id:Option[String])

  implicit val encoderSourceARecord: JsonEncoder[SourceARecord] = DeriveJsonEncoder.gen
  implicit val decoderSourceARecord: JsonDecoder[SourceARecord] = DeriveJsonDecoder.gen


}
