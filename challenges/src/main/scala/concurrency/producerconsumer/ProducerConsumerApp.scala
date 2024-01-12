package concurrency.producerconsumer


import zio._

object ProducerConsumerApp extends ZIOAppDefault {

  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      _  <- ZIO.logInfo("Starting App")
      buffer <- Queue.bounded[Int](1)
      _  <- launchProducerConsumer(buffer)
      _ <- ZIO.sleep(Duration.fromMillis(10000)).debug.forever
      _  <- ZIO.logInfo("Shutting down App")
    } yield()
  }

  def launchProducerConsumer(buffer: Queue[Int]) =
    for {
      _ <- Producer.produce(buffer).fork
      _ <- Consumer.consume(buffer).fork
    } yield ()
}
