package net.mike_dawson.edtechpreflightchecktool.ext

import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Cost
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostBasisEnum
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostTotals
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostTypeEnum
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Plan


val Cost.costAmountAnnual: Float
    get() = when(this.costType) {
        CostTypeEnum.EXPENSE -> costAmount * recurrencePeriodDurationUnit.unitsPerYear
        CostTypeEnum.ASSET -> (costAmount + assetDisposalCost) / (assetLifespanQuantity * assetLifespanUnit.unitsPerYear)
    }


fun Cost.getTotalAnnualCost(plan: Plan): Float{
    val amountAnnual = costAmountAnnual

    val averageStudentsPerSchool = plan.averageClassesPerSchool * plan.averageStudentsPerClass
    val numClasses = (plan.targetNumStudents / plan.averageStudentsPerClass)

    val factor = when(costBasis) {
        CostBasisEnum.LUMP_SUM -> 1f
        CostBasisEnum.PER_SCHOOL -> ((plan.targetNumStudents / averageStudentsPerSchool) / costBasisPerNum)
        CostBasisEnum.PER_STUDENT -> plan.targetNumStudents.toFloat() / costBasisPerNum
        CostBasisEnum.PER_TEACHER -> (numClasses / costBasisPerNum)
        CostBasisEnum.PER_USER -> (plan.targetNumStudents + numClasses) / costBasisPerNum
    }

    return amountAnnual * factor
}

fun Cost.getTotalAnnualMarginalCostPerStudent(plan: Plan) : Float {
    return when(costBasis) {
        CostBasisEnum.LUMP_SUM -> 0f
        else -> {
            getTotalAnnualCost(plan) / plan.targetNumStudents
        }
    }
}

fun Cost.getAnnualTotals(plan: Plan): CostTotals {
    val totalAnnualCost = getTotalAnnualCost(plan)
    return CostTotals(
        forId = id,
        totalCost = totalAnnualCost,
        totalMarginalCostPerStudent = getTotalAnnualMarginalCostPerStudent(plan),
        totalCostPerStudent = totalAnnualCost / plan.targetNumStudents,
    )
}

fun List<CostTotals>.sumCostTotals(forId: String = ""): CostTotals {
    return CostTotals(
        forId = forId,
        totalCost = this.sumOf { it.totalCost.toDouble() }.toFloat(),
        totalMarginalCostPerStudent = this.sumOf { it.totalMarginalCostPerStudent.toDouble() }.toFloat(),
        totalCostPerStudent = this.sumOf { it.totalCostPerStudent.toDouble() }.toFloat(),
    )
}
