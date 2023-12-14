package com.hs.code.challenge.invitationservice.domain

import zio.{Scope, ZIO}
import zio.test.{Spec, TestEnvironment, ZIOSpecDefault, assertTrue}

object InvitationCalculatorTestZio extends ZIOSpecDefault{

  val firstTest = suite("woof")(
    test("cat") {
      for {
        result <- ZIO.succeed(true)
      } yield assertTrue(result)
    }
  )

  override def spec: Spec[TestEnvironment with Scope, Any] = suite("dog")(
    firstTest
  )
}
