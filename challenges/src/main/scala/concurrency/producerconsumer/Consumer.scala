package concurrency.producerconsumer

//import zio.Config.LocalDateTime
import zio._

import java.time.LocalDateTime
object Consumer {

  def consume(buffer: Queue[Int]): ZIO[Any, Nothing, Int] = {

    val consumeResult = for {
      startTime <- ZIO.succeed(LocalDateTime.now());
      _ <- ZIO.logInfo(s"Consumer will consume an item at ${startTime}")
      itemTaken  <- buffer.take
      consumedTime <-  ZIO.succeed(LocalDateTime.now());
      _ <- ZIO.logInfo(s"Consumer consumed item $itemTaken at ${consumedTime}")
      napDuration <- ZIO.succeed(Duration.fromMillis(4000))
      napTime <- ZIO.succeed(LocalDateTime.now());
     _  <- ZIO.logInfo(s"Consumer will take a nap for $napDuration millis at $napTime")
      _  <- ZIO.sleep(napDuration)
    } yield itemTaken
    consumeResult.debug.forever
  }

}
