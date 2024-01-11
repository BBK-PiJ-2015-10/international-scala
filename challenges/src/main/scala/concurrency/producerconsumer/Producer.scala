package concurrency.producerconsumer

import zio._
import scala.util.Random

object Producer {
  def produce(buffer: Queue[Int]): ZIO[Any, Nothing, Boolean] = {
    val offerItem = Random.between(0, 100)
    for {
      _ <- ZIO.logInfo(s"Producer offering item $offerItem")
      result <- buffer.offer(offerItem)
    } yield result
  }
  
}

