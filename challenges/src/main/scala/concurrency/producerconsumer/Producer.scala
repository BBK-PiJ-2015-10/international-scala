package concurrency.producerconsumer

import zio._

import java.time.LocalDateTime
import scala.util.Random

object Producer {
  def produce(buffer: Queue[Int]): ZIO[Any, Nothing, Boolean] = {
    val produceResult  = for {
      offerItem <- ZIO.succeed(Random.between(0, 1000))
      startTime <- ZIO.succeed(LocalDateTime.now());
      _ <- ZIO.logInfo(s"Producer will offer item $offerItem at $startTime")
      result <- buffer.offer(offerItem)
      offeredTime <- ZIO.succeed(LocalDateTime.now());
      _ <- ZIO.logInfo(s"Producer offered item $offerItem at $offeredTime")
      napDuration <- ZIO.succeed(Duration.fromMillis(5000))
      napTime <- ZIO.succeed(LocalDateTime.now());
      _  <- ZIO.logInfo(s"Producer will take a nap for $napDuration millis at $napTime")
      _  <- ZIO.sleep(napDuration)
    } yield result
    produceResult.debug.forever
  }

}

