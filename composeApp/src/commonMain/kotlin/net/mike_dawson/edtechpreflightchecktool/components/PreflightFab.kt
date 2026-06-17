package net.mike_dawson.edtechpreflightchecktool.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import net.mike_dawson.edtechpreflightchecktool.app.AppUiState
import net.mike_dawson.edtechpreflightchecktool.app.FabUiState

@Composable
fun PreflightFab(
    appUiStateVal: AppUiState
) {
    ExtendedFloatingActionButton(
        modifier = Modifier.testTag("floating_action_button")
            .padding(16.dp),
        onClick = appUiStateVal.fabState.onClick,
        text = {
            Text(
                modifier = Modifier.testTag("floating_action_button_text"),
                text = appUiStateVal.fabState.text?.let {
                    uiTextStringResource(it)
                } ?: ""
            )
        },
        icon = {
            val imageVector = when (appUiStateVal.fabState.icon) {
                FabUiState.FabIcon.ADD -> Icons.Default.Add
                FabUiState.FabIcon.EDIT -> Icons.Default.Edit
                else -> null
            }
            if (imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null,
                )
            }
        }
    )
}