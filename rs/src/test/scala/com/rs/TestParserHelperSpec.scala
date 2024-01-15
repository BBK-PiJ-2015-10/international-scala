package com.rs


import org.scalatest.funsuite.AnyFunSuite
import com.rs.parser.Parser
import com.rs.service.external.source.client.ApiEntities.SourceARecord

class TestParserHelperSpec extends AnyFunSuite {

  test("properly formed source A with ok and id record should be parsed") {

    val jsonStringResponse = "{\n    \"status\": \"ok\",\n    \"id\": \"5c7dabe02f3b3b2c751afef4873c1813\"\n}"

    val maybeSourceARecord = Parser.jsonStringToSourceARecord(jsonStringResponse)
    val expectedRecords = SourceARecord("ok", Some("5c7dabe02f3b3b2c751afef4873c1813"))

    assert(maybeSourceARecord.get == expectedRecords)

  }

  test("properly formed source A with ok and no id record should be parsed") {

    val jsonStringResponse = "{\"status\": \"done\"}"
    val maybeSourceARecord = Parser.jsonStringToSourceARecord(jsonStringResponse)
    val expectedRecords = SourceARecord("done", None)

    assert(maybeSourceARecord.get == expectedRecords)

  }

  test("malformed source A response should return none") {

    val jsonStringResponse = "woof"
    val maybeSourceARecord = Parser.jsonStringToSourceARecord(jsonStringResponse)

    assert(maybeSourceARecord == None)

  }

}
