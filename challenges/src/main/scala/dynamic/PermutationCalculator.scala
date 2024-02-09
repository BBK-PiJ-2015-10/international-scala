package dynamic

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

object PermutationCalculator {

  def calculatePermutations(word: String): List[String] = {
    helper(List(), word)
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
        val range = Range(0, word.length+1)
        println(word.length)
        for (r <- range) {
          val (s1, s2) = word.splitAt(r)
          println(s"r at $r has s1: $s1 and s2 $s2")
          val updated = s1 + character + s2
          updatedPermutations.addOne(updated)
        }
        updatedPermutations.toList
      }
    }
  }


}
