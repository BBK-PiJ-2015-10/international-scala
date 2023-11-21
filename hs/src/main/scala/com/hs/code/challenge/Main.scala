package com.hs.code.challenge

import com.hs.code.challenge.partnerservice.api.PartnerWebService
import com.hs.code.challenge.partnerservice.repo.{DataFetcherUtils, PartnerServiceRepo}
import zio.Console.printLine
import zio._
import zio.http._
import zio.http.Server


object Main extends ZIOAppDefault {
  override def run =
    Server.serve(PartnerWebService.app)
      .provide(Server.default,
        PartnerServiceRepo.layer
      )

  //printLine("Welcome to your first ZIO app!")
}