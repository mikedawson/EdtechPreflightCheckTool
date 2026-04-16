package net.mike_dawson.edtechpreflightchecktool.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.mike_dawson.edtechpreflightchecktool.app.ActionBarButtonUiState
import net.mike_dawson.edtechpreflightchecktool.datalayer.datasource.CurrencyDataSource
import net.mike_dawson.edtechpreflightchecktool.datalayer.datasource.PlanDataSource
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Cost
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostCategory
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Currency
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Intervention
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Plan
import net.mike_dawson.edtechpreflightchecktool.ext.asUiText
import net.mike_dawson.edtechpreflightchecktool.ext.replaceOrAppend
import net.mike_dawson.edtechpreflightchecktool.nav.CostEditDest
import net.mike_dawson.edtechpreflightchecktool.nav.InterventionEditDest
import net.mike_dawson.edtechpreflightchecktool.nav.NavCommand
import net.mike_dawson.edtechpreflightchecktool.nav.NavResultReturner
import net.mike_dawson.edtechpreflightchecktool.nav.PlanDetailDest
import net.mike_dawson.edtechpreflightchecktool.nav.PlanEditDest
import kotlin.uuid.Uuid

data class PlanEditUiState(
    val plan: Plan = Plan(),
    val currencyOptions: List<Currency> = emptyList(),
)

class PlanEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val dataSource: PlanDataSource,
    private val currencyDataSource: CurrencyDataSource,
    private val navResultReturner: NavResultReturner,
) : BaseViewModel(savedStateHandle) {

    private val _uiState = MutableStateFlow(PlanEditUiState())

    val uiState: StateFlow<PlanEditUiState> = _uiState.asStateFlow()

    private val routeDest: PlanEditDest = savedStateHandle.toRoute()

    val planId = routeDest.id ?: Uuid.random().toString()

    init {
        _appUiState.update {
            it.copy(
                title = "Edit plan".asUiText(),
                actionBarButtonState = ActionBarButtonUiState(
                    visible = true,
                    text = "Save".asUiText(),
                    onClick = ::onClickSave,
                )
            )
        }

        val loadPlanId = routeDest.id
        if(loadPlanId == null) {
            _uiState.update { prev ->
                prev.copy(plan = prev.plan.copy(id = planId))
            }
        }else {
            viewModelScope.launch {
                val currencies = currencyDataSource.getCurrencies()
                _uiState.update { it.copy(currencyOptions = currencies) }

                dataSource.get(loadPlanId)?.also { plan ->
                    _uiState.update { prev ->
                        prev.copy(plan = plan)
                    }
                }

                viewModelScope.launch {
                    navResultReturner.filteredResultFlowForKey(
                        CostEditViewModel.RESULT_KEY_COST
                    ).collect {
                        val costResult = it.result as? Cost ?: return@collect
                        val newCostCategoryId: String? = savedStateHandle[KEY_ADD_COST_TO_CATEGORY]

                        _uiState.update { prev ->
                            var updatedExisting = false
                            val updatedCostCategories = prev.plan.costCategories.map { costCategory ->
                                costCategory.copy(
                                    costs = costCategory.costs.map { costInCategory ->
                                        if(costInCategory.id == costResult.id) {
                                            updatedExisting = true
                                            costResult
                                        }else {
                                            costInCategory
                                        }
                                    }
                                )
                            }

                            prev.copy(
                                plan = prev.plan.copy(
                                    costCategories = if(updatedExisting) {
                                        updatedCostCategories
                                    }else {
                                        prev.plan.costCategories.map { costCategory ->
                                            if(costCategory.id == newCostCategoryId) {
                                                costCategory.copy(
                                                    costs = costCategory.costs + costResult
                                                )
                                            }else {
                                                costCategory
                                            }
                                        }
                                    }
                                )
                            )
                        }
                    }
                }

                viewModelScope.launch {
                    navResultReturner.filteredResultFlowForKey(
                        InterventionEditViewModel.RESULT_KEY_INTERVENTION
                    ).collect { result ->
                        val intervention = result.result as? Intervention ?: return@collect

                        _uiState.update { prev ->
                            prev.copy(
                                plan = prev.plan.copy(
                                    interventions = prev.plan.interventions.replaceOrAppend(
                                        element = intervention,
                                        replacePredicate = { it.id == intervention.id }
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }


    }

    fun onPlanChange(
        plan: Plan
    ) {
        _uiState.update { prev ->
            prev.copy(plan = plan)
        }
    }

    fun onClickNewCost(
        costCategory: CostCategory
    ) {
        val newCost = Cost(
            id = Uuid.random().toString(),
            name = "",
        )
        savedStateHandle[KEY_ADD_COST_TO_CATEGORY] = costCategory.id

        _navCommandFlow.tryEmit(
            NavCommand.Navigate(
                destination = CostEditDest.create(newCost, uiState.value.plan.currency.code)
            )
        )
    }

    fun onClickDeleteCost(
        cost: Cost
    ) {
        _uiState.update { prev ->
            prev.copy(
                plan = prev.plan.copy(
                    costCategories = prev.plan.costCategories.map { costCategory ->
                        costCategory.copy(
                            costs = costCategory.costs.filter { it.id != cost.id }
                        )
                    }
                )
            )
        }
    }

    fun onClickDeleteIntervention(
        intervention: Intervention
    ) {
        _uiState.update { prev ->
            prev.copy(
                plan = prev.plan.copy(
                    interventions = prev.plan.interventions.filter { it.id != intervention.id }
                )
            )
        }
    }

    fun onClickCost(
        cost: Cost
    ) {
        _navCommandFlow.tryEmit(
            NavCommand.Navigate(
                destination = CostEditDest.create(cost, uiState.value.plan.currency.code)
            )
        )
    }

    fun onClickIntervention(
        intervention: Intervention
    ) {
        _navCommandFlow.tryEmit(
            NavCommand.Navigate(
                destination = InterventionEditDest.create(intervention)
            )
        )
    }

    fun onClickAddIntervention() {
        _navCommandFlow.tryEmit(
            NavCommand.Navigate(
                destination = InterventionEditDest.create(Intervention())
            )
        )
    }

    fun onChangeCategoryName(
        categoryId: String,
        newName: String
    ) {
        _uiState.update { prev ->
            prev.copy(
                plan = prev.plan.copy(
                    costCategories = prev.plan.costCategories.map {
                        if(it.id == categoryId) {
                            it.copy(name = newName)
                        }else {
                            it
                        }
                    }
                )
            )
        }
    }

    fun onClickDeleteCostCategory(
        category: CostCategory
    ) {
        _uiState.update { prev ->
            prev.copy(
                plan = prev.plan.copy(
                    costCategories = prev.plan.costCategories.filter { it.id != category.id }
                )
            )
        }
    }

    fun onClickAddCostCategory() {
        _uiState.update { prev ->
            prev.copy(
                plan = prev.plan.copy(
                    costCategories = prev.plan.costCategories + CostCategory(
                        name = "",
                        id = Uuid.random().toString(),
                        costs = emptyList()
                    )
                )
            )
        }
    }


    fun onClickSave() {
        dataSource.store(uiState.value.plan)

        if(routeDest.id == null){
            _navCommandFlow.tryEmit(
                NavCommand.Navigate(
                    destination = PlanDetailDest(id = planId),
                    popUpToClass = PlanEditDest::class,
                    popUpToInclusive = true,
                )
            )
        }else {
            _navCommandFlow.tryEmit(NavCommand.PopUp())
        }

    }

    companion object {

        const val KEY_ADD_COST_TO_CATEGORY = "add_cost_to_category"

    }

}