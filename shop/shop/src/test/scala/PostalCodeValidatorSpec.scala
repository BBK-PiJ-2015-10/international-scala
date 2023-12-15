
import org.scalatest.funsuite.AnyFunSuite

class PostalCodeValidatorSpec extends AnyFunSuite{


  test("Testing postal code regex1") {
    val postalCode = "B0P 1X0"
    val providence = "NS"
    val valid = PostalCodeValidator.isValidPostalCode(postalCode,providence)
    assert(valid == true)
  }

  test("Testing postal code regex2") {
    val postalCode = "b0P 1X0"
    val providence = "NS"
    val valid = PostalCodeValidator.isValidPostalCode(postalCode, providence)
    assert(valid == false)
  }

  test("Testing postal code regex3") {
    val postalCode = "X0A0H0 1X0"
    val providence = "NS"
    val valid = PostalCodeValidator.isValidPostalCode(postalCode, providence)
    assert(valid == false)
  }

  test("Testing postal code regex4") {
    val postalCode = "B0P 1X"
    val providence = "NS"
    val valid = PostalCodeValidator.isValidPostalCode(postalCode, providence)
    assert(valid == false)
  }

  test("Testing postal code regex5") {
    val postalCode = "B0P 1X0B"
    val providence = "NS"
    val valid = PostalCodeValidator.isValidPostalCode(postalCode, providence)
    assert(valid == false)
  }

  test("Testing postal code regex6") {
    val postalCode = "X0A 0H0"
    val providence = "NS"
    val valid = PostalCodeValidator.isValidPostalCode(postalCode, providence)
    assert(valid == true)
  }




}
