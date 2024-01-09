package concurrency.semaphore

import zio.Console.printLine
import zio.{Duration, Ref, ZIO}

import java.io.IOException

object SemaphoreExample {

  def queryDatabase(connections: Ref[Int]): ZIO[Any, IOException, Unit] = {
    connections.updateAndGet(_ + 1).flatMap { n =>
      printLine(s"Acquiring now $n connections") *>
        ZIO.sleep(Duration.fromMillis(1000)) *>
        printLine(s"Closing now ${n-1} connections")
    }
  }



}
