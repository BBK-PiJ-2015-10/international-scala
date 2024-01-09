package concurrency.semaphore


import zio.Console.printLine
import zio.{Duration, Ref, Scope, Semaphore, ZIO, ZIOAppArgs, ZIOAppDefault}


//AP: TODO
// Implement consumer. Make the ZIO run for ever
object ScalaSemaphoreRunner extends ZIOAppDefault {
  override def run: ZIO[Environment with ZIOAppArgs with Scope, Any, Any] = {
    for {
      ref <- Ref.make(0)
      semaphore <- Semaphore.make(1)
      queryDatabase = semaphore.withPermit(SemaphoreExample.queryDatabase(ref))
      queryKafka = semaphore.withPermit(SemaphoreExample.queryKafka(ref))
      left <- queryDatabase.fork
      right <- queryKafka.fork
      //culon <- (ZIO.foreachParDiscard(1 to 2)(_ => queryDatabase)
     //_   <- ZIO.foreachParDiscard(1 to 10)(_ => queryDatabase *> queryKafka)
      _  <- ZIO.sleep(Duration.fromMillis(40000))
    } yield ()
  }

}