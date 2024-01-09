package concurrency.semaphore

import zio.Console.printLine
import zio.{Duration, Ref, ZIO}

import java.io.IOException

object SemaphoreExample {

  def queryDatabase(connections: Ref[Int]): ZIO[Any, IOException, Unit] = {
    connections.updateAndGet(_ + 1).flatMap { n =>
      printLine(s"Acquiring database now $n connections") *>
        ZIO.sleep(Duration.fromMillis(2000)) *>
        printLine(s"Closing database now ${n-1} connections")
    }
  }

  def queryKafka(connections: Ref[Int]): ZIO[Any, IOException, Unit] = {
    connections.updateAndGet(_ + 1).flatMap { n =>
      printLine(s"Acquiring kafka now $n connections") *>
        ZIO.sleep(Duration.fromMillis(2000)) *>
        printLine(s"Closing kafka now ${n-1} connections")
    }
  }



}
