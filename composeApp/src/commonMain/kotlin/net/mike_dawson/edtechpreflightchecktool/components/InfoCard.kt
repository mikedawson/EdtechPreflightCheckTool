package net.mike_dawson.edtechpreflightchecktool.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InfoCard(
    headlineText: String,
    contentText: String,
){
    //See //As per https://developer.android.com/develop/ui/compose/components/card?_gl=1*1ij2cas*_ga*MTY5MjA3MzQ2Mi4xNzU4NjUzOTc0*_ga_QPQ2NRV856*czE3NzYzMjYwMjMkbzI5JGcxJHQxNzc2MzI2MDQ0JGozOSRsMCRoMA..
    Box(
        modifier = Modifier.padding(16.dp)
    ) {
        Card(
            colors = CardDefaults.outlinedCardColors(),
            border = BorderStroke(1.dp, Color.Black),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp),
            ) {
                Text(headlineText)
                Text(
                    style = MaterialTheme.typography.headlineLarge,
                    text = contentText,
                )
            }
        }
    }

}