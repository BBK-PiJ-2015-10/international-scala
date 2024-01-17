package com.rs.parser

import com.rs.source.client.ApiEntities.SourceRecord
import com.rs.sink.client.ApiEntities.SinkResponse
import zio.json.DecoderOps

import scala.util.{Failure, Success, Try}
import scala.xml.{Elem, XML}


object Parser {

  def jsonStringToSinkResponse(jsonString: String): Option[SinkResponse] = {
     val sinkResponse = jsonString.fromJson[SinkResponse]
     sinkResponse match {
       case Left(_) => None
       case Right(sr) => Some(sr)
     }
  }

  def jsonStringToSourceRecord(jsonString: String): Option[SourceRecord] = {
    val sourceRecord  = jsonString.fromJson[SourceRecord]
    sourceRecord match {
      case Left(_) => None
      case Right(r) => Some(r)
    }
  }

  private def xmlElementToSourceRecord(xmlResponse: Elem): Option[SourceRecord] = {
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


  def xmlStringToSourceRecord(xmlString: String): Option[SourceRecord] = {
    val xmlResponse = Try(XML.loadString(xmlString))
    val response = xmlResponse match {
      case Success(elem) => xmlElementToSourceRecord(elem)
      case Failure(_) => None
    }
    response
  }








}
