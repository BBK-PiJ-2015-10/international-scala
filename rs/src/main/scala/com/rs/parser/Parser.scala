package com.rs.parser

import com.rs.service.external.source.client.ApiEntities.{SourceRecord}
import zio.json.DecoderOps

import scala.xml.XML


object Parser {

  def jsonStringToSourceARecord(jsonString: String): Option[SourceRecord] = {
    val response  = jsonString.fromJson[SourceRecord]
    val maybeRecord = response match {
      case Left(_) => None
      case Right(r) => Some(r)
    }
    maybeRecord
  }


  def xmlStringToSourceBRecord(xmlString: String): Option[SourceRecord] = {
    val xmlResponse = XML.loadString(xmlString)
    val msgLabel = xmlResponse.label
    if (msgLabel == "msg"){
      val maybeContent = xmlResponse.child
      if (maybeContent.isEmpty) {
        None
      } else {
        val content = maybeContent.head
        val contentLabel = content.label
        contentLabel match {
          case "done" => Some(SourceRecord(contentLabel,None))
          case "id" =>
            val maybeIdValue  = content.attribute("value")
            maybeIdValue match {
              case None => None
              case Some(ids) =>
                Some(SourceRecord("ok",Some(ids.head.text)))
            }
          case _ => None
        }
      }
    } else {
      None
    }
  }



}
