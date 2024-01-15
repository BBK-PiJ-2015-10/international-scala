package com.rs.parser

import com.rs.service.external.source.client.ApiEntities.SourceARecord
import zio.json.DecoderOps

object Parser {

  def jsonStringToSourceARecord(jsonString: String): Option[SourceARecord] = {
    val response  = jsonString.fromJson[SourceARecord]
    val maybeRecord = response match {
      case Left(_) => None
      case Right(r) => Some(r)
    }
    maybeRecord
  }



}
