
import com.typesafe.scalalogging.Logger
import org.scalatest.funsuite.AnyFunSuite

class PostalCodeValidatorSpec extends AnyFunSuite{

  val logger = Logger(classOf[PostalCodeValidatorSpec])


  test("Testing is valid postalCode") {

    val testCases: Map[(String,String),Boolean] = Map(
      ("B0P 1X0","NS") -> true,
      ("b0p 1X0","NS") -> false,
      ("M5W 1E6","QC") -> false,
      ("X0A 0H0","NU") -> true,
      ("V0F G5A","BC") -> false,
      ("X0A0H0 NU","NU") -> false,
      ("V0G 3E3","BC") -> true,
      ("B0P 1X","NS") -> false,
      ("B0P 1X0B","NS") -> false,
    )

    for (tc <- testCases){
      val valid = PostalCodeValidator.isValidPostalCode(tc._1._1,tc._1._2)
      if (valid != tc._2){
        logger.error(s"For ${tc._1} failed to meet expected ${tc._2}")
      }
      assert(valid == tc._2)
    }

    //val postalCode = "B0P 1X0"
    //val providence = "NS"
    //val valid = PostalCodeValidator.isValidPostalCode(postalCode,providence)
    //assert(valid == true)
  }

//   test("Testing postal code regex2") {
//    val postalCode = "b0P 1X0"
//    val providence = "NS"
//    val valid = PostalCodeValidator.isValidPostalCode(postalCode, providence)
//    assert(valid == false)
//  }
//
//  test("Testing postal code regex3") {
//    val postalCode = "X0A0H0 1X0"
//    val providence = "NS"
//    val valid = PostalCodeValidator.isValidPostalCode(postalCode, providence)
//    assert(valid == false)
//  }
//
//  test("Testing postal code regex4") {
//    val postalCode = "B0P 1X"
//    val providence = "NS"
//    val valid = PostalCodeValidator.isValidPostalCode(postalCode, providence)
//    assert(valid == false)
//  }
//
//  test("Testing postal code regex5") {
//    val postalCode = "B0P 1X0B"
//    val providence = "NS"
//    val valid = PostalCodeValidator.isValidPostalCode(postalCode, providence)
//    assert(valid == false)
//  }
//
//  test("Testing postal code regex6") {
//    val postalCode = "X0A 0H0"
//    val providence = "NS"
//    val valid = PostalCodeValidator.isValidPostalCode(postalCode, providence)
//    assert(valid == true)
//  }




}
