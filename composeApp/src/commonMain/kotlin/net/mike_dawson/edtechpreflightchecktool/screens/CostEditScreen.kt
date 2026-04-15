package net.mike_dawson.edtechpreflightchecktool.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.mike_dawson.edtechpreflightchecktool.components.PreflightExposedDropDownMenuField
import net.mike_dawson.edtechpreflightchecktool.components.UstadNumberTextField
import net.mike_dawson.edtechpreflightchecktool.components.defaultItemPadding
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Cost
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostBasisEnum
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.CostTypeEnum
import net.mike_dawson.edtechpreflightchecktool.viewmodel.CostEditUiState
import net.mike_dawson.edtechpreflightchecktool.viewmodel.CostEditViewModel

@Composable
fun CostEditScreen(
    viewModel: CostEditViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    CostEditScreen(
        uiState = uiState,
        onChange = viewModel::onChange,
    )
}

@Composable
fun CostEditScreen(
    uiState: CostEditUiState,
    onChange: (Cost) -> Unit = { }
) {
    val cost = uiState.cost
    Column(
        modifier = Modifier.verticalScroll(
            state = rememberScrollState()
        ).fillMaxSize()
    ) {
        if(cost != null) {
            OutlinedTextField(
                modifier = Modifier.defaultItemPadding().fillMaxWidth(),
                singleLine = true,
                label = {
                    Text("Name")
                },
                value = uiState.cost?.name ?: "",
                onValueChange = {
                    onChange(cost.copy(name = it))
                }
            )

            PreflightExposedDropDownMenuField(
                modifier = Modifier.defaultItemPadding().fillMaxWidth(),
                value = cost.costType,
                label = {
                    Text("Type")
                },
                options = CostTypeEnum.entries.toList(),
                onOptionSelected = {
                    onChange(cost.copy(costType = it))
                },
                itemLabel = { it.displayName },
            )

            when(cost.costType) {
                CostTypeEnum.EXPENSE -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(Modifier.width(16.dp))
                        Text("Every")
                        Spacer(Modifier.width(8.dp))

                        UstadNumberTextField(
                            modifier = Modifier.padding(vertical = 8.dp).width(96.dp),
                            value = cost.recurrencePeriodQuantity.toFloat(),
                            onValueChange = {
                                onChange(cost.copy(recurrencePeriodQuantity = it.toInt()))
                            }
                        )
                        Spacer(Modifier.width(8.dp))
                        PreflightExposedDropDownMenuField(
                            modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth().weight(1f),
                            value = cost.recurrencePeriodDurationUnit,
                            options = uiState.durationOptions,
                            onOptionSelected = {
                                onChange(cost.copy(recurrencePeriodDurationUnit = it))
                            },
                            itemLabel = { it.displayName },
                        )
                        Spacer(Modifier.width(16.dp))

                    }
                }

                CostTypeEnum.ASSET -> {

                }
            }

            PreflightExposedDropDownMenuField(
                modifier = Modifier.defaultItemPadding().fillMaxWidth(),
                value = cost.costBasis,
                label = {
                    Text("Cost basis")
                },
                options = CostBasisEnum.entries.toList(),
                onOptionSelected = {
                    onChange(cost.copy(costBasis = it))
                },
                itemLabel = { it.displayName }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val isUnitBased = cost.costBasis.isUnitBased

                UstadNumberTextField(
                    modifier = Modifier
                        .let {
                            if(isUnitBased) {
                                it.width(128.dp)
                                   .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                            } else {
                                it.fillMaxWidth()
                                    .defaultItemPadding()
                            }
                        },
                    label = { Text("USD") },
                    value = cost.costAmount,
                    onValueChange = {
                        onChange(cost.copy(costAmount = it))
                    }
                )

                Spacer(Modifier.width(8.dp))
                Text("per")

                if(isUnitBased) {
                    val trailingText = cost.costBasis.placeholderTrailingText

                    UstadNumberTextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 16.dp),
                        value = cost.costBasisPerNum,
                        onValueChange = {
                            onChange(cost.copy(costBasisPerNum = it))
                        },
                        trailingIcon = if(trailingText != null) {
                            {
                                Text(
                                    text = trailingText,
                                    modifier = Modifier.padding(end = 16.dp)
                                )
                            }
                        }else {
                            null
                        }
                    )
                }

            }
        }

    }
}

@Preview
@Composable
fun CostEditScreenPreview() {
    CostEditScreen(
        CostEditUiState(
            cost = Cost(id = "", name = "expensive")
        )
    )
}