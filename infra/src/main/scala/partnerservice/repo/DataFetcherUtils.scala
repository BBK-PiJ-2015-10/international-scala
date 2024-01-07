package partnerservice.repo

import partnerservice.entities.ApiEntities.Partners
import zio.ZIO
import zio.json._

import scala.io.Source

object DataFetcherUtils {

  def fetchPartnersData(): ZIO[Any, Throwable, Partners] = for {
    jsonPartners <- getJsonString(LOAD_PARTNER_DATA)
    partners <- ZIO.fromEither(JsonDecoder[Partners].decodeJson(jsonPartners))
      .tapError(e => ZIO.logError(e))
      .mapError(e => new Throwable(e))
  } yield partners

  private val LOAD_PARTNER_DATA =
    "src/main/scala/partnerservice/repo/partners.json"

  private def getJsonString(fileName: String) = {
    ZIO.acquireReleaseWith(
      ZIO.attempt(Source.fromFile(fileName)) zipLeft ZIO.logInfo(s"opening file $fileName")
    )(x => ZIO.succeed(x.close()) zipLeft ZIO.logInfo(s"Closing file $fileName")) {
      file => ZIO.attempt(file.getLines().mkString)
    }
  }

}
