package concurrency.producerconsumer


import zio._

object ProducerConsumerApp extends ZIOAppDefault {

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      _ <- ZIO.logInfo("WOOF1")
      _ <- ZIO.sleep(Duration.fromMillis(1000))
      _ <- ZIO.logInfo("WOOF2")
    } yield ()
  }
}
