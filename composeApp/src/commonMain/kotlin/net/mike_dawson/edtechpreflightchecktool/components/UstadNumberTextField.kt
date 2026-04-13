package net.mike_dawson.edtechpreflightchecktool.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.roundToInt


fun Float.roundTo(decimalPlaces: Int = 0): Float {
    val factor = 10.0.pow(decimalPlaces.toDouble())
    return ((this * factor).roundToInt() / factor).toFloat()
}

/**
 * Remove the decimal point if this float has nothing after the decimal point.
 */
fun Float.toDisplayString(
    decimalPlaces: Int = 2
): String {
    val strVal = toString()
    return if(round(this) == this) {
        strVal.substringBefore('.')
    }else {
        this.roundTo(decimalPlaces).toString().removeSuffix("0")
    }
}

/**
 * See
 *  https://dev.to/pchmielowski/jetpack-compose-textfield-which-accepts-and-emits-value-other-than-string-1g3o
 *
 *  @param value Float - decimal point values are not currently supported, however this will be
 *  an option in future. Hence type is set as float.
 */
@Composable
fun UstadNumberTextField(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number
    ),
    supportingText: (@Composable () -> Unit)? = null,
    unsetDefault: Float = 0f,
    singleLine: Boolean = true,
) {
    fun Float.numberString() = if(this == unsetDefault) "" else toDisplayString()

    var rawValue by remember {
        mutableStateOf(value.numberString())
    }

    LaunchedEffect(value) {
        if((rawValue.toFloatOrNull() ?: unsetDefault) != value) {
            rawValue = value.numberString()
        }
    }

    OutlinedTextField(
        value = rawValue,
        modifier = modifier,
        onValueChange = { text ->
            val filteredText = text.filter { it.isDigit() || it == '.' }
            rawValue = filteredText
            val floatVal = filteredText.toFloatOrNull() ?: unsetDefault
            onValueChange(floatVal)
        },
        isError = isError,
        label = label,
        placeholder = placeholder,
        enabled = enabled,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        supportingText = supportingText,
        singleLine = singleLine,
    )
}
