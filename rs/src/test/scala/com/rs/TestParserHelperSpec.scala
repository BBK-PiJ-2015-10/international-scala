package com.rs


import org.scalatest.funsuite.AnyFunSuite
import com.rs.parser.Parser
import com.rs.service.external.source.client.ApiEntities.{SourceARecord, SourceBRecord}

import scala.xml.XML

class TestParserHelperSpec extends AnyFunSuite {

  test("properly formed source A with ok and id record should be parsed") {

    val jsonStringResponse = "{\n    \"status\": \"ok\",\n    \"id\": \"5c7dabe02f3b3b2c751afef4873c1813\"\n}"

    val maybeSourceARecord = Parser.jsonStringToSourceARecord(jsonStringResponse)
    val expectedRecord = SourceARecord("ok", Some("5c7dabe02f3b3b2c751afef4873c1813"))

    assert(maybeSourceARecord.get == expectedRecord)

  }

  test("properly formed source A with ok and no id record should be parsed") {

    val jsonStringResponse = "{\"status\": \"done\"}"
    val maybeSourceARecord = Parser.jsonStringToSourceARecord(jsonStringResponse)
    val expectedRecord = SourceARecord("done", None)

    assert(maybeSourceARecord.get == expectedRecord)

  }

  test("malformed source A response should return none") {

    val jsonStringResponse = "woof"
    val maybeSourceARecord = Parser.jsonStringToSourceARecord(jsonStringResponse)

    assert(maybeSourceARecord == None)

  }

  test("done record source B should be parsed") {

    val xmlRecordResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><done/></msg>"

    val maybeSourceARecord = Parser.xmlStringToSourceBRecord(xmlRecordResponse)
    val expectedRecord = SourceBRecord("done", None)

    assert(maybeSourceARecord.get == expectedRecord)

  }

  test("continue record source B returns") {

    val continueRecordSourceB = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><id value=\"5c7dabe02f3b3b2c751afef4873c1813\"/></msg>"

    val xmlResponse = XML.loadString(continueRecordSourceB)

    val msgLabel = xmlResponse.label
    val child = xmlResponse.child.head
    val childLabel = child.label
    val culon = child.attribute("value")
    //val childLabel = child.label

    // this return msg
    println(s"FUCKER1 $msgLabel")
    println(s"FUCKER2 $child")
    println(s"FUCKER3 $childLabel")
    println(s"FUCKER4 $culon")


    //println(xmlResponse)

    //val other = xmlResponse.child

    //val children = xmlResponse \ "msg"

    //println(other)

    //    val children = xml \ "symbol"


    //assert(true == true)


    assert(true == true)
  }


}
