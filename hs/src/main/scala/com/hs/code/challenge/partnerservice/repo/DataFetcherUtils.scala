package com.hs.code.challenge.partnerservice.repo

import zio.ZIO
import zio.stream.{ZSink, ZStream}

import scala.io.Source

object DataFetcherUtils {


  def fetchPartnersData() =
    loadTestSettingsRows(LOAD_PARTNER_DATA)

  private val LOAD_PARTNER_DATA=
    "src/main/scala/com/hs/code/challenge/partnerservice/repo/partners.json"

  def fetchPartnersDataScala() = ZIO.attempt(Source.fromFile(LOAD_PARTNER_DATA).getLines.mkString)

  private def getlines(testInputFile: String): ZStream[Any, Throwable, String] =
    ZStream
      .acquireReleaseWith(
        ZIO.attempt(Source.fromFile(testInputFile)) zipLeft ZIO.logDebug(s"opening file $testInputFile")
      )(x => ZIO.succeed(x.close()) zipLeft ZIO.logDebug(s"closing file $testInputFile"))
      .flatMap { is =>
        ZStream.fromIterator(is.getLines())
      }

  private def loadTestSettingsRows(testInputFile: String): ZIO[Any, Throwable, Set[String]] = {
    val rowStringStreams: ZStream[Any, Throwable, String] = getlines(testInputFile)
    rowStringStreams
      //.via(toArrayStrings)
      //.via(toLoadTestSettingsRows)
      //.via(toLoadTestScenario)
      .run(ZSink.collectAllToSet)
  }



}
