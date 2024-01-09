package concurrency.semaphore


import zio.Console.printLine
import zio.{Duration, Ref, Scope, Semaphore, ZIO, ZIOAppArgs, ZIOAppDefault}

object ScalaSemaphoreRunner extends ZIOAppDefault {
  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] = {
    for {
      ref <- Ref.make(0)
      semaphore <- Semaphore.make(1)
      query = semaphore.withPermit(SemaphoreExample.queryDatabase(ref))
      _   <- ZIO.foreachParDiscard(1 to 30)(_ => query)
    } yield ()
  }

}