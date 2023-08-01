package org.pc
package service

trait ContributionCalculator {

  def calculateContribution(salary: Double, desiredContributionPercentage: Double, maxContributionAmount: Double, matchingBands: Map[Double, Double]): Double

}

case class ContributionCalculatorImpl() extends ContributionCalculator {
  override def calculateContribution(salary: Double, desiredContributionPercentage: Double, maxContributionAmount: Double, matchingBands: Map[Double, Double]):Double = {
    var totalAmountContribution : Double = 0.00
    var totalLeftContributionPercentage : Double = desiredContributionPercentage
    var totalLeftContributionAmount : Double = maxContributionAmount
    val sortedBands = matchingBands.keySet.toList.sorted
    for (band <- sortedBands){
      if (totalLeftContributionAmount > 0 && totalLeftContributionPercentage > 0){
        // simple case
        val matchingPercentage = matchingBands.get(band)
        if (totalLeftContributionPercentage - band < 0) {
          // confirm amount is not exceeded
          val matchingAmount : Double = salary * totalLeftContributionPercentage * matchingPercentage.get
          if ((matchingAmount + totalAmountContribution) < 0) {

          } else {
            totalAmountContribution += matchingAmount
            totalLeftContributionPercentage = 0
          }
        } else {
          // confirm amount is not exceeded
          val matchingAmount = salary * band * matchingPercentage

          totalAmountContribution += matchingAmount
          totalLeftContributionPercentage -= band

        }
      }
    }
    totalAmountContribution
  }
}
