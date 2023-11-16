package com.hs.code.challenge

import zio.Console.printLine
import zio._

object Main extends ZIOAppDefault {
  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    printLine("Welcome to your first ZIO app!")
}