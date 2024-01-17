package com.rs


import org.scalatest.funsuite.AnyFunSuite
import com.rs.parser.Parser
import com.rs.source.client.ApiEntities.SourceRecord

class TestParserHelperSpec extends AnyFunSuite {

  test("properly formed json source with ok and id record should be parsed") {

    val jsonStringResponse = "{\n    \"status\": \"ok\",\n    \"id\": \"5c7dabe02f3b3b2c751afef4873c1813\"\n}"

    val maybeSourceARecord = Parser.jsonStringToSourceRecord(jsonStringResponse)
    val expectedRecord = SourceRecord("ok", Some("5c7dabe02f3b3b2c751afef4873c1813"))

    assert(maybeSourceARecord.get == expectedRecord)

  }

  test("properly formed json source with ok and no id record should be parsed") {

    val jsonStringResponse = "{\"status\": \"done\"}"
    val maybeSourceARecord = Parser.jsonStringToSourceRecord(jsonStringResponse)
    val expectedRecord = SourceRecord("done", None)

    assert(maybeSourceARecord.get == expectedRecord)

  }

  test("malformed json source should return none") {

    val jsonStringResponse = "woof"
    val maybeSourceARecord = Parser.jsonStringToSourceRecord(jsonStringResponse)

    assert(maybeSourceARecord == None)

  }

  test("done record xml source should be parsed") {

    val xmlRecordResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><done/></msg>"

    val maybeSourceBRecord = Parser.xmlStringToSourceRecord(xmlRecordResponse)
    val expectedRecord = SourceRecord("done", None)

    assert(maybeSourceBRecord.get == expectedRecord)

  }

  test("continue xml record source should be parsed") {

    val xmlRecordResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><id value=\"5c7dabe02f3b3b2c751afef4873c1813\"/></msg>"

    val maybeSourceBRecord = Parser.xmlStringToSourceRecord(xmlRecordResponse)

    val expectedRecord = SourceRecord("ok", Some("5c7dabe02f3b3b2c751afef4873c1813"))

    assert(maybeSourceBRecord.get == expectedRecord)

  }

  test("malformed no value xml record source should be parsed") {

    val xmlRecordResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><id woof=\"5c7dabe02f3b3b2c751afef4873c1813\"/></msg>"

    val maybeSourceBRecord = Parser.xmlStringToSourceRecord(xmlRecordResponse)

    val expectedRecord = None

    assert(maybeSourceBRecord == expectedRecord)

  }

  test("malformed no id element xml record source should be parsed") {

    val xmlRecordResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><cat value=\"5c7dabe02f3b3b2c751afef4873c1813\"/></msg>"

    val maybeSourceBRecord = Parser.xmlStringToSourceRecord(xmlRecordResponse)

    val expectedRecord = None

    assert(maybeSourceBRecord == expectedRecord)

  }

  test("malformed no msg element xml record source should be parsed") {

    val xmlRecordResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><dog><id value=\"5c7dabe02f3b3b2c751afef4873c1813\"/></dog>"

    val maybeSourceBRecord = Parser.xmlStringToSourceRecord(xmlRecordResponse)

    val expectedRecord = None

    assert(maybeSourceBRecord == expectedRecord)

  }

  test("malformed no xml record source should be parsed") {

    val xmlRecordResponse = "cat"

    val maybeSourceBRecord = Parser.xmlStringToSourceRecord(xmlRecordResponse)

    val expectedRecord = None

    assert(maybeSourceBRecord == expectedRecord)

  }


}
