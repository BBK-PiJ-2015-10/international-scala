
import com.typesafe.scalalogging.Logger
import org.scalatest.funsuite.AnyFunSuite

class PostalCodeValidatorSpec extends AnyFunSuite {

  val logger = Logger(classOf[PostalCodeValidatorSpec])


  test("Testing is valid postalCode") {

    val testCases: Map[(String, String), Boolean] = Map(
      ("B0P 1X0", "NS") -> true,
      ("b0p 1X0", "NS") -> false,
      ("M5W 1E6", "QC") -> false,
      ("X0A 0H0", "NU") -> true,
      ("V0F G5A", "BC") -> false,
      ("X0A0H0 NU", "NU") -> false,
      ("V0G 3E3", "BC") -> true,
      ("B0P 1X", "NS") -> false,
      ("B0P 1X0B", "NS") -> false,
    )

    for (tc <- testCases) {
      val valid = PostalCodeValidator.isValidPostalCode(tc._1._1, tc._1._2)
      if (valid != tc._2) {
        logger.error(s"For ${tc._1} failed to meet expected ${tc._2}")
      }
      assert(valid == tc._2)
    }

  }

  test("Testing resolveProvidence") {

    val testCases: Map[String, Option[String]] = Map(
      "P3E 5P9" -> Some("ON"),
      "P3e 5o9" -> None,
      "P3e N0M" -> None,
      "X0E 0T0" -> Some("NT"),
      "Q1Q 1Q1" -> None,
      "Y1A 1V6" -> Some("YT"),
      "Y1A1V6" -> None,
      "PE3 5P" -> None,
      "Y1X 1V66" -> None,
    )

    for (tc <- testCases) {
      val providence = PostalCodeValidator.resolveProvidence(tc._1)
      if (providence != tc._2) {
        logger.error(s"For ${tc._1} failed to meet expected ${tc._2}")
      }
      assert(providence == tc._2)
    }

  }


}
