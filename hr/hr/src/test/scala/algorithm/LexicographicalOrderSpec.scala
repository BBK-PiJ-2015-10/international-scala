package algorithm

import org.scalatest.funsuite.AnyFunSuite



class LexicographicalOrderSpec extends AnyFunSuite{

  test("sample") {

    val word1 = "lmno"

    val word2 = "lmon"

    val result = LexicographicalOrder.lexiCompare(word1,word2)

    //-1

    println(result)

  }

  test("sample2") {

    val word1 = "dkhc"

    val word2 = "hcdk"

    val result = LexicographicalOrder.lexiCompare(word1, word2)

    //-1

    println(result)

  }

}
