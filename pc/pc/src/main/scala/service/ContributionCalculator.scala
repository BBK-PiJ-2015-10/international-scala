package org.pc
package service

trait ContributionCalculator {

  def calculateContribution(salary: Double, desiredContributionPercentage: Double, maxContributionAmount: Double, matchingBands: Map[Double, Double]): Double

}

case class ContributionCalculatorImpl() extends ContributionCalculator {
  override def calculateContribution(salary: Double, desiredContributionPercentage: Double, maxContributionAmountRule: Double, matchingBands: Map[Double, Double]): Double = {
    var maxContributionAmount: Double = 0.00
    if (salary * desiredContributionPercentage <= maxContributionAmountRule) {
      maxContributionAmount = salary * desiredContributionPercentage
    } else {
      maxContributionAmount = maxContributionAmountRule
    }
    calculateTotalContribution(salary, 0.0, 0.0, maxContributionAmount, 0.0, matchingBands.keySet.toList.sorted, matchingBands)
  }

  private def calculateTotalContribution(salary: Double, totalEmployeeContributionAmount: Double, totalCompanyContributionAmount: Double, maxContributionAmount: Double, previousBand: Double, bands: List[Double], matchingBands: Map[Double, Double]): Double = {
    if (totalEmployeeContributionAmount >= maxContributionAmount) {
      totalEmployeeContributionAmount + totalCompanyContributionAmount
    } else {
      if (bands.isEmpty) {
        // No more matching offer by employer
        val incrementalEmployeeContribution = maxContributionAmount - totalEmployeeContributionAmount
        val updatedTotalEmployeeContribution = incrementalEmployeeContribution + totalEmployeeContributionAmount
        updatedTotalEmployeeContribution + totalCompanyContributionAmount
      } else {
        val band = bands.head
        val matchPercentage: Double = matchingBands(band)
        val incrementalBand: Double = band - previousBand
        val incrementalEmployeeContribution: Double = salary * incrementalBand
        if (incrementalEmployeeContribution + totalEmployeeContributionAmount <= maxContributionAmount) {
          val updatedTotalEmployeeContribution = incrementalEmployeeContribution + totalEmployeeContributionAmount
          val updatedTotalCompanyContribution = (incrementalEmployeeContribution * matchPercentage) + totalCompanyContributionAmount
          val remainingBands = bands.tail
          calculateTotalContribution(salary, updatedTotalEmployeeContribution, updatedTotalCompanyContribution, maxContributionAmount, band, remainingBands, matchingBands)
        } else {
          val adjustedIncrementalEmployeeContribution = maxContributionAmount - totalEmployeeContributionAmount
          val updatedTotalEmployeeContribution = adjustedIncrementalEmployeeContribution + totalEmployeeContributionAmount
          val updatedTotalCompanyContribution = (adjustedIncrementalEmployeeContribution * matchPercentage) + totalCompanyContributionAmount
          val remainingBands = bands.tail
          calculateTotalContribution(salary, updatedTotalEmployeeContribution, updatedTotalCompanyContribution, maxContributionAmount, band, remainingBands, matchingBands)
        }
      }
    }
  }

}
