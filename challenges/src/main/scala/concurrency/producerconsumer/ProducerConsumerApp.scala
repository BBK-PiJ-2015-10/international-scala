package concurrency.producerconsumer


import zio._

object ProducerConsumerApp extends ZIOAppDefault {


  def fucker(input: Boolean): Boolean = {
    if (input == false) {
      true
    } else {
      false
    }
  }

  def update(input: Boolean) = {
   true
  }


  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    for {
      _  <- ZIO.logInfo("Starting App")
      buffer <- Queue.bounded[Int](1)
      _  <- helper(buffer)
      _  <- ZIO.logInfo("Shutting down App")
    } yield()
  }

  def helper(buffer: Queue[Int]) = ZIO.loopDiscard(1)(_ <= 8, _ + 1) { _ =>
    for {
      _ <- Producer.produce(buffer)
      _ <- Consumer.consume(buffer)
    } yield ()
  }
}
