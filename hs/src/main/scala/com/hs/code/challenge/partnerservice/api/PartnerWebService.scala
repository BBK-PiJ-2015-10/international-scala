package com.hs.code.challenge.partnerservice.api

import com.hs.code.challenge.partnerservice.repo.PartnerServiceRepo
import zio.ZIO
import zio.http._

object PartnerWebService {

  val app: Http[PartnerServiceRepo, Nothing, Request, Response] = Http.collectZIO[Request] {
    case Method.GET -> !! / "woof" =>  {
      (for {
        service   <- ZIO.service[PartnerServiceRepo]
        cat         <- service.fetchPartners()
        response <-  ZIO.succeed(Response.text("woof"))
      } yield response)
        .catchAll(e => ZIO.succeed(Response.text("ale")))
    }

  }

}
