package org.pc
package service

trait ContributionCalculator {

  def calculateContribution(salary: Double, desiredContributionPercentage: Double, maxContributionAmount: Double, matchingBands: Map[Double, Double]): Double

}

case class ContributionCalculatorImpl() extends ContributionCalculator {
  override def calculateContribution(salary: Double, desiredContributionPercentage: Double, maxContributionAmount: Double, matchingBands: Map[Double, Double]):Double = {
    var totalAmountContribution : Double = 0.00
    var totalPercentageContribution : Double = 0.0
    val sortedBands = matchingBands.keySet.toList.sorted
    for (band <- sortedBands){
      if (totalAmountContribution < maxContributionAmount && totalPercentageContribution < desiredContributionPercentage){
        // simple case
        val matchingPercentage = matchingBands.get(band)
        val matchingAmount = salary * band * matchingPercentage
        totalAmountContribution += matchingAmount
        totalPercentageContribution += band
        // case where you exceed the band
      }
    }
    totalAmountContribution
  }
}
