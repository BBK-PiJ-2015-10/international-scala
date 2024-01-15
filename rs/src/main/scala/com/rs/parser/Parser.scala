package com.rs.parser

import com.rs.service.external.source.client.ApiEntities.{SourceARecord, SourceBRecord}
import zio.json.DecoderOps

import scala.xml.XML


object Parser {

  def jsonStringToSourceARecord(jsonString: String): Option[SourceARecord] = {
    val response  = jsonString.fromJson[SourceARecord]
    val maybeRecord = response match {
      case Left(_) => None
      case Right(r) => Some(r)
    }
    maybeRecord
  }


  def xmlStringToSourceBRecord(xmlString: String): Option[SourceBRecord] = {
    val xmlResponse = XML.loadString(xmlString)
    val msgLabel = xmlResponse.label
    if (msgLabel == "msg"){
      val maybeContent = xmlResponse.child
      if (maybeContent.isEmpty) {
        None
      } else {
        val content = maybeContent.head
        val contentLabel = content.label
        if (contentLabel == "done") {
          Some(SourceBRecord(contentLabel,None))
        } else {
          None
        }
      }
    } else {
      None
    }
  }



}
