package com.hs.code.challenge.dataservice.api

import zio.ZIO
import zio.http._
//import zio.json.EncoderOps

object DataWebService {

  val app = Http.collectZIO[Request] {
    case Method.GET -> Root / "data" / "recommendation" =>
      ( for {
        _  <- ZIO.logInfo("woof")
        response <- ZIO.attempt(Response.text("woof"))
      } yield response).catchAll(e =>
        ZIO.succeed(Response.text(s"UnexpectedError $e").withStatus(Status.InternalServerError)))
  }

}
