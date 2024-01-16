package com.rs.parser

import com.rs.service.external.source.client.ApiEntities.SourceRecord
import zio.json.DecoderOps

import scala.util.{Failure, Success, Try}
import scala.xml.{Elem, XML}


object Parser {

  def jsonStringToSourceRecord(jsonString: String): Option[SourceRecord] = {
    val response  = jsonString.fromJson[SourceRecord]
    val maybeRecord = response match {
      case Left(_) => None
      case Right(r) => Some(r)
    }
    maybeRecord
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


  def xmlStringToSourceRecord(xmlString: String) = {
    val xmlResponse = Try(XML.loadString(xmlString))
    val other = xmlResponse match {
      case Success(elem) => xmlElementToSourceRecord(elem)
      case Failure(_) => None
    }
    other
  }








}
