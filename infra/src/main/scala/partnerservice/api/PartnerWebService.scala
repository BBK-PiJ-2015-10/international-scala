package partnerservice.api

import partnerservice.repo.PartnerServiceRepo
import zio.ZIO
import zio.http._
import zio.json.EncoderOps

object PartnerWebService {

  val app: Http[PartnerServiceRepo, Nothing, Request, Response] = Http.collectZIO[Request] {
    case Method.GET -> Root / "partnerservice" / "partners" => {
      (for {
        service <- ZIO.service[PartnerServiceRepo]
        partners <- service.fetchPartners()
        jsonReponse = partners.toJson
        response <- ZIO.succeed(Response.text(jsonReponse))
      } yield response)
        .catchAll(e => ZIO.succeed(
          Response.text(s"UnexpectedError $e").withStatus(Status.InternalServerError)))
    }

  }

}
