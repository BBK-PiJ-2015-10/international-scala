package com.hs.code.challenge.resultservice.api

import zio.ZIO
import zio.http._
import com.hs.code.challenge.resultservice.entities.ApiEntities._

import zio.json._

object ResultWebService {

  val app = Http.collectZIO[Request] {
    case req @ Method.POST -> Root / "candidateTest" / "v3" / "problem" / "result" =>
      (for {
        jsonString<- req.body.asString
        _  <- ZIO.logInfo(s"woof2 $jsonString")
        decoded= jsonString.fromJson[Country]
        countries <- ZIO.fromEither(decoded)
        _  <- ZIO.logInfo(s"woof3 ${countries.name}")
        response <- ZIO.attempt(Response.text("Keep going"))
      } yield response).catchAll(e =>
        ZIO.succeed(Response.text(s"UnexpectedError $e").withStatus(Status.InternalServerError)))
  }

}
