package com.rs

import zio.Console.printLine
import zio._

object Main extends ZIOAppDefault {

  val sourceUrl = "http://localhost:7299"


  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] =
    printLine("Welcome to your first ZIO app!")
}