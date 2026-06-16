package net.mike_dawson.edtechpreflightchecktool.ext

import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Cost
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostAmountRange
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostBasisEnum
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostTotals
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostTypeEnum
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Plan


val Cost.expenseToAnnualCostMultiplier: Float
    get() = (recurrencePeriodDurationUnit.unitsPerYear.toFloat() / recurrencePeriodQuantity.toFloat())

val Cost.assetToAnnualCostDenominator: Float
    get() =(assetLifespanQuantity * (1.toFloat() / assetLifespanUnit.unitsPerYear))

val Cost.costAmountAnnual: CostAmountRange
    get() = when(this.costType) {
        CostTypeEnum.EXPENSE -> {
            costAmount * expenseToAnnualCostMultiplier
        }

        CostTypeEnum.ASSET -> {
            (costAmount + assetDisposalCost) / assetToAnnualCostDenominator
        }
    }

fun Cost.costBasisFactor(plan: Plan): Float {
    val averageStudentsPerSchool = plan.averageClassesPerSchool * plan.averageStudentsPerClass
    val numClasses = (plan.targetNumStudents / plan.averageStudentsPerClass)

    return when(costBasis) {
        CostBasisEnum.LUMP_SUM -> 1f
        CostBasisEnum.PER_SCHOOL -> ((plan.targetNumStudents / averageStudentsPerSchool) / costBasisPerNum)
        CostBasisEnum.PER_STUDENT -> plan.targetNumStudents.toFloat() / costBasisPerNum
        CostBasisEnum.PER_TEACHER -> (numClasses / costBasisPerNum)
        CostBasisEnum.PER_USER -> (plan.targetNumStudents + numClasses) / costBasisPerNum
    }
}

fun Cost.getTotalAnnualCost(plan: Plan): CostAmountRange{
    return costAmountAnnual * costBasisFactor(plan)
}

fun Cost.getTotalAnnualMarginalCostPerStudent(plan: Plan) : CostAmountRange {
    return when(costBasis) {
        CostBasisEnum.LUMP_SUM -> CostAmountRange(0f, 0f)
        else -> {
            getTotalAnnualCost(plan) / plan.targetNumStudents
        }
    }
}

fun Cost.getAnnualTotals(plan: Plan): CostTotals {
    val totalAnnualCost = getTotalAnnualCost(plan)
    return CostTotals(
        forId = id,
        totalCost = getTotalAnnualCost(plan),
        totalMarginalCostPerStudent = getTotalAnnualMarginalCostPerStudent(plan),
        totalCostPerStudent = totalAnnualCost / plan.targetNumStudents,
    )
}

fun List<CostTotals>.sumCostTotals(forId: String = ""): CostTotals {
    return CostTotals(
        forId = forId,
        totalCost = this.map { it.totalCost }.sumCostRanges(),
        totalMarginalCostPerStudent = this.map { it.totalMarginalCostPerStudent }.sumCostRanges(),
        totalCostPerStudent = this.map { it.totalCostPerStudent }.sumCostRanges(),
    )
}
