package com.hs.code.challenge

import com.hs.code.challenge.partnerservice.repo.{DataFetcherUtils, PartnerServiceRepo}
import zio.Console.printLine
import zio._

object Main extends ZIOAppDefault {
  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] = (for {
    _ <- ZIO.logInfo("WOOF")
    repo <- ZIO.service[PartnerServiceRepo]
    resp <- repo.fetchPartners()
    _ <- ZIO.logInfo(s"CULON $resp")
  } yield ()).provide(
    PartnerServiceRepo.layer
  )

  //printLine("Welcome to your first ZIO app!")
}