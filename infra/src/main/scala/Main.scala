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

//$ export DOCKER_USERNAME=<username>  // e.g: johndoe
//$ export DOCKER_REGISTRY=<registry>  // e.g: docker.io
//$ sbt -Ddocker.username=$NAMESPACE -Ddocker.registry=$DOCKER_REGISTRY docker:publish