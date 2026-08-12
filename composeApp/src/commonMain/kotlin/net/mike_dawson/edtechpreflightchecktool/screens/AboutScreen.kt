package net.mike_dawson.edtechpreflightchecktool.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import net.mike_dawson.edtechpreflightchecktool.components.defaultItemPadding
import net.mike_dawson.edtechpreflightchecktool.viewmodel.AboutViewModel

@Composable
fun AboutScreen(
    viewModel: AboutViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .defaultItemPadding()
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            buildAnnotatedString {
                append("The EdTech pre-flight check tool builds on the work of EdTech Hub.\n\n")
                append("Copyright 2026 EdTech Hub. This is open source software licensed under the MIT License.\n\n")
                append("This software includes open-source libraries, copyright their respective owners. See https://github.com/mikedawson/EdtechPreflightCheckTool for details.\n\n")
                append("The tool was developed for organizations and analysts working in the Ed Tech space who need rapid cost-efficiency estimates that are reasonably accurate but not obsessively detailed.\n\n")
                append("This tool is designed to be:\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Flexible.")
                }
                append("It runs on a laptop or a phone.\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Secure. ")
                }
                append("Data you enter are stored on your device, not on a server.\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Built for Comparability. ")
                }
                append("Because cost-efficiency estimates are most useful when compared across interventions, the tool is designed to estimate costs for multiple interventions simultaneously, making it easier to identify the most cost-efficient option. The interventions can include development costs, implementation costs, or both combined.\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Methodologically Pragmatic. ")
                }
                append("The tool generates cost-efficiency metrics across different outputs (e.g., students reached, teachers trained, parents consulted, etc.). This allows for ease in comparing different types of interventions with useful metrics such as the total cost per year, marginal cost per student, ROI ranges, and years of learning or learning-adjusted years of schooling (LAYS).\n")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Customizable. ")
                }
                append("Standard cost categories are included (i.e., intervention direct costs, infrastructure, training and support), but you can also include other categories so it fits your intervention model. You can begin by editing the example provided or start your estimates from scratch.\n\n")
                withStyle(style = ParagraphStyle(textAlign = TextAlign.Center)) {
                    append("Because this tool is currently a proof of concept, your feedback is welcome! Please send comments or suggestions to joel@edtechhub.org")
                }
            }
        )
    }
}