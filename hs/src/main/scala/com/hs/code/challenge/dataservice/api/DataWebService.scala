package com.hs.code.challenge.dataservice.api

import zio.ZIO
import zio.http._
import com.hs.code.challenge.dataservice.entities.ApiEntities._

import zio.json._

object DataWebService {

  val app = Http.collectZIO[Request] {
    case req @ Method.GET -> Root / "data" / "recommendation" =>
      (for {
        jsonString<- req.body.asString
        _  <- ZIO.logInfo(s"woof2 $jsonString")
        decoded = jsonString.fromJson[Recommendation]
        recommendation <- ZIO.fromEither(decoded)
        _  <- ZIO.logInfo(s"woof3 ${recommendation.serviceAddress}")
        response <- ZIO.attempt(Response.text("woof"))
      } yield response).catchAll(e =>
        ZIO.succeed(Response.text(s"UnexpectedError $e").withStatus(Status.InternalServerError)))
  }

}
