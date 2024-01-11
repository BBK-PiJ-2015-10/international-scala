package concurrency.producerconsumer

import zio._
object Consumer {

  def consume(buffer: Queue[Int]): ZIO[Any, Nothing, Int] = {
    for {
      _ <- ZIO.logInfo(s"Consumer will consume an item")
      itemTaken  <- buffer.take
      _ <- ZIO.logInfo(s"Consumer consumed item $itemTaken")
    } yield itemTaken
  }

}
