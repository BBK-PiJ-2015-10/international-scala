import partnerservice.api.PartnerWebService
import partnerservice.repo.PartnerServiceRepo
import zio._
import zio.Console.printLine
import zio._
import zio.http.Server

object Main extends ZIOAppDefault {

  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    Server.serve(PartnerWebService.app)
      .provide(Server.default,
        PartnerServiceRepo.layer
      )

}