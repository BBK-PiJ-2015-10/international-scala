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
