package dynamic

import org.scalatest.funsuite.AnyFunSuite

class PermutationCalculatorSpec extends AnyFunSuite {

  test("updatePermutations of a single letter") {
    val word = ""
    val letter = "a"
    val expectedResponse = List("a")
    val response = PermutationCalculator.updatePermutations(word, letter)
    assert(response == expectedResponse)

  }

  test("updatePermutations of an empty letter") {
    val word = "abc"
    val letter = ""
    val expectedResponse = List("abc")
    val response = PermutationCalculator.updatePermutations(word, letter)
    assert(response == expectedResponse)

  }

  test("updatePermutations of two letters") {
    val word = "a"
    val letter = "b"
    val expectedResponse = List("ab", "ba")
    val response = PermutationCalculator.updatePermutations(word, letter)
    assert(response.sorted == expectedResponse.sorted)
  }

  test("updatePermutations of three letters") {
    val word = "ab"
    val letter = "c"
    val expectedResponse = List("cab", "acb", "abc")
    val response = PermutationCalculator.updatePermutations(word, letter)
    assert(response.sorted == expectedResponse.sorted)
  }

  test("permutations of empty letter word") {
    val word = ""
    val expectedResponse = List()
    val response = PermutationCalculator.calculatePermutations(word)
    assert(response == expectedResponse)
  }

  test("permutations of one letter word") {
    val word = "a"
    val expectedResponse = List("a")
    val response = PermutationCalculator.calculatePermutations(word)
    assert(response.sorted == expectedResponse.sorted)
  }

  test("permutations of two letter word") {
    val word = "ab"
    val expectedResponse = List("ab", "ba")
    val response = PermutationCalculator.calculatePermutations(word)
    assert(response.sorted == expectedResponse.sorted)
  }

  test("permutations of three letter word") {
    val word = "abc"
    val expectedResponse = List("cab", "acb", "abc", "cba", "bca", "bac")
    val response = PermutationCalculator.calculatePermutations(word)
    assert(response.sorted == expectedResponse.sorted)
  }


}
