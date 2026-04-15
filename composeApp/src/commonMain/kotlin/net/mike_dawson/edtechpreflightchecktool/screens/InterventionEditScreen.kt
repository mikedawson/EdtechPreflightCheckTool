package net.mike_dawson.edtechpreflightchecktool.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.mike_dawson.edtechpreflightchecktool.components.PreflightExposedDropDownMenuField
import net.mike_dawson.edtechpreflightchecktool.components.UstadNumberTextField
import net.mike_dawson.edtechpreflightchecktool.components.defaultItemPadding
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.Intervention
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.InterventionCategoryEnum
import net.mike_dawson.edtechpreflightchecktool.datalayer.model.InterventionLicenseTypeEnum
import net.mike_dawson.edtechpreflightchecktool.viewmodel.InterventionEditUiState
import net.mike_dawson.edtechpreflightchecktool.viewmodel.InterventionEditViewModel

@Composable
fun InterventionEditScreen(
    viewModel: InterventionEditViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    InterventionEditScreen(
        uiState = uiState,
        onChange = viewModel::onChange,
    )
}

@Composable
fun InterventionEditScreen(
    uiState: InterventionEditUiState,
    onChange: (Intervention) -> Unit = { },
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().defaultItemPadding(),
            value = uiState.intervention.name,
            singleLine = true,
            label = { Text("Name") },
            onValueChange = {
                onChange(uiState.intervention.copy(name = it))
            }
        )

        PreflightExposedDropDownMenuField(
            modifier = Modifier.fillMaxWidth().defaultItemPadding(),
            value = uiState.intervention.category,
            options = InterventionCategoryEnum.entries.toList(),
            onOptionSelected = {
                onChange(uiState.intervention.copy(category = it))
            },
            itemLabel = { it.displayName },
            label = { Text("Category") }
        )

        Text(
            modifier = Modifier.fillMaxWidth().defaultItemPadding(),
            text = "LAYS expected"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            UstadNumberTextField(
                modifier = Modifier.weight(1f).defaultItemPadding(),
                value = uiState.intervention.laysFrom,
                onValueChange = {
                    onChange(uiState.intervention.copy(laysFrom = it))
                }
            )

            Text(" to ")

            UstadNumberTextField(
                modifier = Modifier.weight(1f).defaultItemPadding(),
                value = uiState.intervention.laysTo,
                onValueChange = {
                    onChange(uiState.intervention.copy(laysTo = it))
                }
            )

        }

        PreflightExposedDropDownMenuField(
            modifier = Modifier.fillMaxWidth().defaultItemPadding(),
            value = uiState.intervention.licenseType,
            options = InterventionLicenseTypeEnum.entries.toList(),
            onOptionSelected = {
                onChange(uiState.intervention.copy(licenseType = it))
            },
            itemLabel = { it.displayName },
            label = { Text("License type") },
        )

        ListItem(
            modifier = Modifier.clickable {
                onChange(
                    uiState.intervention.copy(
                        inCountrySupported = !uiState.intervention.inCountrySupported
                    )
                )
            },
            headlineContent = {
                Text("Supports data sovereignty (on-prem/in-country hosting personal data)")
            },
            trailingContent = {
                Checkbox(
                    checked = uiState.intervention.inCountrySupported,
                    onCheckedChange = {
                        onChange(uiState.intervention.copy(inCountrySupported = it))
                    }
                )
            }
        )

        ListItem(
            modifier = Modifier.clickable {
                onChange(
                    uiState.intervention.copy(
                        oAuthSupported = !uiState.intervention.oAuthSupported
                    )
                )
            },
            headlineContent = {
                Text("Supports oAuth authentication (single sign on / identity providers)")
            },
            trailingContent = {
                Checkbox(
                    checked = uiState.intervention.oAuthSupported,
                    onCheckedChange = {
                        onChange(uiState.intervention.copy(oAuthSupported = it))
                    }
                )
            }
        )

        ListItem(
            modifier = Modifier.clickable {
                onChange(
                    uiState.intervention.copy(
                        xApiSupported = !uiState.intervention.xApiSupported
                    )
                )
            },
            headlineContent = {
                Text("Supports xAPI open standard Learner Record Store (LRS)")
            },
            trailingContent = {
                Checkbox(
                    checked = uiState.intervention.xApiSupported,
                    onCheckedChange = {
                        onChange(uiState.intervention.copy(xApiSupported = it))
                    }
                )
            }
        )
    }
}

@Composable
@Preview
fun InterventionEditScreenPreview() {
    InterventionEditScreen(InterventionEditUiState())
}