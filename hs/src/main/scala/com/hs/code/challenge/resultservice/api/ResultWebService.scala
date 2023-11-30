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
        decoded= jsonString.fromJson[List[Country]]
        countries <- ZIO.fromEither(decoded)
        _  <- ZIO.logInfo(s"woof3 ${countries.size}")
        response <- ZIO.attempt(Response.text(s"Submitted ${countries.size} countries"))
      } yield response).catchAll(e =>
        ZIO.succeed(Response.text(s"UnexpectedError $e").withStatus(Status.InternalServerError)))
  }

}
