package dynamic

import org.scalatest.funsuite.AnyFunSuite

class PermutationCalculatorSpec extends AnyFunSuite {

  test("permutation of a single letter") {
    val word = ""
    val letter = "a"
    val expectedResponse = List("a")
    val response = PermutationCalculator.updatePermutations(word,letter)
    assert(response == expectedResponse)

  }

  test("permutation of an empty letter") {
    val word = "abc"
    val letter = ""
    val expectedResponse = List("abc")
    val response = PermutationCalculator.updatePermutations(word,letter)
    assert(response == expectedResponse)

  }

  test("permutation of two letters") {
    val word = "a"
    val letter = "b"
    val expectedResponse = List("ab","ba")
    val response = PermutationCalculator.updatePermutations(word,letter)
    assert(response.sorted == expectedResponse.sorted)
  }

  test("permutation of three letters") {
    val word = "ab"
    val letter = "c"
    val expectedResponse = List("cab","acb","abc")
    val response = PermutationCalculator.updatePermutations(word,letter)
    assert(response.sorted == expectedResponse.sorted)
  }




}
