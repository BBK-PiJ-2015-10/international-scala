package org.fig
package service

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
  describe("multiplication") {
    it("should give back 0 if multiplying by 0") {
      assert(calculator.multiply(572389, 0) == 0)
      assert(calculator.multiply(-572389, 0) == 0)
      assert(calculator.multiply(0, 0) == 0)
    }
  }

  describe("division") {
    it("should throw a math error if dividing by 0") {
      assertThrows[ArithmeticException](calculator.divide(57238, 0))
    }
  }


}
