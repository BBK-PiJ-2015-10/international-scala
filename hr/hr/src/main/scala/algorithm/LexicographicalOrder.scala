package algorithm

object LexicographicalOrder {

  def lexiCompare(input1: String,input2: String) = {
    input1.compareTo(input2)
  }

//  def nextLexi(input: String) = {
//    if (input.length <=1){
//      None
//    } else {
//      val cat  = helperNextLexi("")
//      Some("wood")
//    }
//  }

}

//def helperNextLexi(cum: String,eval: String): Option[String] = {
//  if (eval.length <= 1 ) {
//    None
//  } else {
//    if (eval.length == 2 ){
//      val pos1 = eval.length-1
//      val pos2 = pos1-1
//      val w1 =
//      None
//    }
//    else {
//      None
//    }
//  }
//}