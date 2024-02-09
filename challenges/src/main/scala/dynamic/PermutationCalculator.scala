package dynamic

import scala.collection.mutable.ListBuffer

object PermutationCalculator {

  def calculatePermutations(input: String): List[String] = {
    if (input.isEmpty) {
      List()
    } else {
      val word: List[String] = input.toCharArray.map(_.toString).toList
      word.foldRight(List(""))((a, b) => helper(b, a))
    }
  }

  def helper(ongoingPermutations: List[String], letter: String): List[String] = {
    ongoingPermutations.map(p => updatePermutations(p, letter)).flatten
  }

  def updatePermutations(word: String, character: String): List[String] = {
    if (word.isEmpty) {
      List(character)
    } else {
      if (character.isBlank) {
        List(word)
      } else {
        val updatedPermutations: ListBuffer[String] = ListBuffer()
        val range = Range(0, word.length + 1)
        println(word.length)
        for (r <- range) {
          val (s1, s2) = word.splitAt(r)
          val updated = s1 + character + s2
          updatedPermutations.addOne(updated)
        }
        updatedPermutations.toList
      }
    }
  }


}
