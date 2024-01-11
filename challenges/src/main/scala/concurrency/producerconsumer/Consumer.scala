package concurrency.producerconsumer

import zio._
object Consumer {

  def consume(buffer: Queue[Int]): ZIO[Any, Nothing, Int] = {
    for {
      _ <- ZIO.logInfo(s"Consumer will consume an item")
      itemTaken  <- buffer.take
      _ <- ZIO.logInfo(s"Consumer consumed item $itemTaken")
      _  <- ZIO.logInfo(s"Consumer will take a nap for 500 millis")
      _  <- ZIO.sleep(Duration.fromMillis(1000))

    } yield itemTaken
  }

}
