package org.fig
package service

import org.fig.model.{Movie, User, Watch}
import org.fig.repo.{WatchRepo, WatchRepoImpl}
import org.scalatest.funspec.AnyFunSpec

class WatchRepoSpec extends AnyFunSpec {

  class Calculator {
    def add(a: Int, b: Int) = a + b

    def subtract(a: Int, b: Int) = a - b

    def multiply(a: Int, b: Int) = a * b

    def divide(a: Int, b: Int) = a / b
  }

  val calculator = new Calculator

  // can nest as many levels deep as you like
  describe("fetching watch") {

    it("an empty repo should return an empty list") {
      val watchRepo = WatchRepoImpl()
      val existingWatchList = watchRepo.findAll()
      assert(existingWatchList.isEmpty)
    }

  }

  describe("storing watch") {

    it("testing ordering on saving operation") {
      val watchRepo: WatchRepo = WatchRepoImpl()
      val watchService: WatchService = WatchServiceImpl(watchRepo)

      val user = User("Alex")

      val initialWatch = Watch("id-1", user, Movie("Top Gun"), 1)
      watchService.saveWatch(initialWatch)
      val secondWatch = Watch("id-2", user, Movie("Hello World"), 1)
      watchService.saveWatch(secondWatch)
      val thirdWatch = Watch("id-3", user, Movie("Hi"), 3)
      watchService.saveWatch(secondWatch)
      val existingWatchList = watchRepo.findAll()

      println(existingWatchList)

      assert(existingWatchList.nonEmpty)
    }


    it("should give back 0 if multiplying by 0 adasd") {
      assert(calculator.multiply(572389, 0) == 0)
      assert(calculator.multiply(-572389, 0) == 0)
      assert(calculator.multiply(0, 0) == 0)
    }
  }


}
