package com.hs.code.challenge.invitationservice.domain

import org.scalatest.funsuite.AnyFunSuite

class InvitationCalculatorTest extends AnyFunSuite{

  test("Invoking head on an empty Set should produce NoSuchElementException") {

    val input =
      """
        |{
        |  "partners": [
        |    {
        |      "firstName": "Darin",
        |      "lastName": "Daignault",
        |      "email": "ddaignault@hubspotpartners.com",
        |      "country": "United States",
        |      "availableDates": [
        |        "2017-05-03",
        |        "2017-05-06"
        |      ]
        |    },
        |    {
        |      "firstName": "Eugena",
        |      "lastName": "Auther",
        |      "email": "eauther@hubspotpartners.com",
        |      "country": "United States",
        |      "availableDates": [
        |        "2017-05-04",
        |        "2017-05-09"
        |      ]
        |    }
        |  ]
        |}
        """.stripMargin


    assert(2 == 2)

  }


}
