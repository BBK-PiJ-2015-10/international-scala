package com.hs.code.challenge

import com.hs.code.challenge.partnerservice.repo.DataFetcherUtils
import zio.Console.printLine
import zio._

object Main extends ZIOAppDefault {
  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] = for {
    _ <- ZIO.logInfo("WOOF")
    pd  <- DataFetcherUtils.fetchPartnersDataScala
    _  <- ZIO.logInfo(s"CULON $pd")
  } yield ()

    //printLine("Welcome to your first ZIO app!")
}