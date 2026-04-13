package net.mike_dawson.edtechpreflightchecktool.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.mike_dawson.edtechpreflightchecktool.components.UstadNumberTextField
import net.mike_dawson.edtechpreflightchecktool.components.defaultItemPadding
import net.mike_dawson.edtechpreflightchecktool.datalayer.Plan
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanEditUiState
import net.mike_dawson.edtechpreflightchecktool.viewmodel.PlanEditViewModel

@Composable
fun PlanEditScreen(
    viewModel: PlanEditViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    PlanEditScreen(
        uiState = uiState,
        onPlanChange = viewModel::onPlanChange,
    )
}

@Composable
fun PlanEditScreen(
    uiState: PlanEditUiState,
    onPlanChange: (Plan) -> Unit = { }
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().defaultItemPadding(),
            value = uiState.plan.name,
            singleLine = true,
            label = { Text("Name") },
            onValueChange = {
                onPlanChange(uiState.plan.copy(name = it))
            }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().defaultItemPadding(),
            value = uiState.plan.country,
            singleLine = true,
            label = { Text("Country") },
            onValueChange = {
                onPlanChange(uiState.plan.copy(country = it))
            }
        )

        UstadNumberTextField(
            value = uiState.plan.averageStudentsPerClass,
            modifier = Modifier.defaultItemPadding().fillMaxWidth(),
            label = { Text("Average students per class") },
            onValueChange = {
                onPlanChange(uiState.plan.copy(averageStudentsPerClass = it))
            }
        )

        UstadNumberTextField(
            value = uiState.plan.averageClassesPerSchool,
            modifier = Modifier.defaultItemPadding().fillMaxWidth(),
            label = { Text("Average classes per school") },
            onValueChange = {
                onPlanChange(uiState.plan.copy(averageClassesPerSchool = it))
            }
        )

        UstadNumberTextField(
            value = uiState.plan.targetNumStudents.toFloat(),
            modifier = Modifier.defaultItemPadding().fillMaxWidth(),
            label = { Text("Target number of students") },
            onValueChange = {
                onPlanChange(uiState.plan.copy(targetNumStudents = it.toInt()))
            }
        )

        OutlinedButton(
            modifier = Modifier.fillMaxWidth().defaultItemPadding(),
            onClick = {

            }
        ) {
            Text("Add intervention")
        }
    }
}


@Preview
@Composable
fun PlanEditScreenPreview() {
    PlanEditScreen(PlanEditUiState())
}
