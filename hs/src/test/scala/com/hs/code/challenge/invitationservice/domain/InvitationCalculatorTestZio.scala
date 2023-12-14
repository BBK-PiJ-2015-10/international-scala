package com.hs.code.challenge.invitationservice.domain

import com.hs.code.challenge.invitationservice.service.external.client.ApiEntities.Partners
import zio.json.DecoderOps
import zio.{Scope, ZIO}
import zio.test.{Spec, TestEnvironment, ZIOSpecDefault, assertTrue}

object InvitationCalculatorTestZio extends ZIOSpecDefault{

  val firstTest = suite("woof")(
    test("cat") {

      val jsonResponse =
        """
          |{
          |  "partners": [
          |    {
          |      "firstName": "Janyce",
          |      "lastName": "Gustison",
          |      "email": "jgustison@hubspotpartners.com",
          |      "country": "Spain",
          |      "availableDates": [
          |        "2017-04-29",
          |        "2017-04-30",
          |        "2017-05-01"
          |      ]
          |    },
          |    {
          |      "firstName": "Tifany",
          |      "lastName": "Mozie",
          |      "email": "tmozie@hubspotpartners.com",
          |      "country": "Spain",
          |      "availableDates": [
          |        "2017-04-28",
          |        "2017-04-29",
          |        "2017-05-01",
          |        "2017-05-04"
          |      ]
          |    },
          |    {
          |      "firstName": "Temple",
          |      "lastName": "Affelt",
          |      "email": "taffelt@hubspotpartners.com",
          |      "country": "Spain",
          |      "availableDates": [
          |        "2017-04-28",
          |        "2017-04-29",
          |        "2017-05-02",
          |        "2017-05-04"
          |      ]
          |    },
          |    {
          |      "firstName": "Robyn",
          |      "lastName": "Yarwood",
          |      "email": "ryarwood@hubspotpartners.com",
          |      "country": "Spain",
          |      "availableDates": [
          |        "2017-04-29",
          |        "2017-04-30",
          |        "2017-05-02",
          |        "2017-05-03"
          |      ]
          |    },
          |    {
          |      "firstName": "Shirlene",
          |      "lastName": "Filipponi",
          |      "email": "sfilipponi@hubspotpartners.com",
          |      "country": "Spain",
          |      "availableDates": [
          |        "2017-04-30",
          |        "2017-05-01"
          |      ]
          |    },
          |    {
          |      "firstName": "Oliver",
          |      "lastName": "Majica",
          |      "email": "omajica@hubspotpartners.com",
          |      "country": "Spain",
          |      "availableDates": [
          |        "2017-04-28",
          |        "2017-04-29",
          |        "2017-05-01",
          |        "2017-05-03"
          |      ]
          |    },
          |    {
          |      "firstName": "Wilber",
          |      "lastName": "Zartman",
          |      "email": "wzartman@hubspotpartners.com",
          |      "country": "Spain",
          |      "availableDates": [
          |        "2017-04-29",
          |        "2017-04-30",
          |        "2017-05-02",
          |        "2017-05-03"
          |      ]
          |    }
          |  ]
          |}
          |""".stripMargin


      for {
        partners <- ZIO.fromEither(jsonResponse.fromJson[Partners])
        result = InvitationCalculator.processPartners(partners.partners)
        _   <- ZIO.logInfo(s"$result")
      } yield assertTrue(true)
    }
  )

  override def spec: Spec[TestEnvironment with Scope, Any] = suite("dog")(
    firstTest
  )
}
