
object PostalCodeValidator {


  val dictionary = Map("A" -> "NL",
    "B" -> "NS",
    "C" -> "PE",
    "E" -> "NB",
    "G" -> "QC",
    "H" -> "QC",
    "J" -> "QC",
    "K" -> "ON",
    "L" -> "ON",
    "M" -> "ON",
    "N" -> "ON",
    "P" -> "ON",
    "R" -> "MB",
    "S" -> "SK",
    "T" -> "AB",
    "V" -> "BC",
    "X0A" -> "NU",
    "X0B" -> "NU",
    "X0C" -> "NU",
    "X0E" -> "NT",
    "X0G" -> "NT",
    "X1A" -> "NT",
     "Y"  -> "YT"
  )

  val pattern = "([A-Z][0-9][A-Z0-9]) ([0-9][A-Z][0-9])".r

  val specialCases = "X[0-9][A-Z0-9]".r

  def isValidPostalCode(postalCode: String, providenceCode: String) : Boolean = {
    // validate postalCode is properly formed
    if (isPostalCodePatternValid(postalCode)){
      val firstAlphas = postalCode.substring(0,3)
      var lookUpKey = postalCode.substring(0,1)
      if (isSpecialCase(firstAlphas)){
        lookUpKey = firstAlphas
      }
      val optProvidenceCode = dictionary.get(lookUpKey)
        if (optProvidenceCode.isEmpty) {
          false
        } else {
          optProvidenceCode.get == providenceCode
        }
    } else {
      false
    }
  }

  def resolveProvidence(postalCode: String)= {
    if (isPostalCodePatternValid(postalCode)) {
      val firstAlphas = postalCode.substring(0, 3)
      var lookUpKey = postalCode.substring(0, 1)
      if (isSpecialCase(firstAlphas)) {
        lookUpKey = firstAlphas
      }
      dictionary.get(lookUpKey)
    } else {
      None
    }
  }

  private def isPostalCodePatternValid(postalCode: String) = {
    pattern.matches(postalCode)
  }

  private def isSpecialCase(postalCodeFirstAlphas: String) = {
    specialCases.matches(postalCodeFirstAlphas)
  }


}
