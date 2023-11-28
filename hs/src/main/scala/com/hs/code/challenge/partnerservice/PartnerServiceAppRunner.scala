package com.hs.code.challenge.partnerservice

import com.hs.code.challenge.partnerservice.api.PartnerWebService
import com.hs.code.challenge.partnerservice.repo.PartnerServiceRepo
import zio._
import zio.http.Server


object PartnerServiceAppRunner extends ZIOAppDefault {
  override def run =
    Server.serve(PartnerWebService.app)
      .provide(Server.default,
        PartnerServiceRepo.layer
      )

  //printLine("Welcome to your first ZIO app!")
}