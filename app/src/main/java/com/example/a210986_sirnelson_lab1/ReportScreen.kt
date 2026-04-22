package com.example.a210986_sirnelson_lab1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a210986_sirnelson_lab1.ui.theme.A210986_SirNelson_Lab1Theme
import com.example.a210986_sirnelson_lab1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(viewModel: TrashViewModel) {
    val userData = viewModel.userData.observeAsState()   // auto-fill area from Profile
    val reports = viewModel.reportData.observeAsState(emptyList())

    var issueType by remember { mutableStateOf("Missed Collection") }
    var description by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(true) } // start expanded

    val issueOptions = listOf(
        "Missed Collection",
        "Late Collection",
        "Damaged Bin",
        "Overflowing Bin",
        "Uncollected Street Waste",
        "Other"
    )

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 🔑 Bright background image
            Image(
                painter = painterResource(id = R.drawable.trashbackground),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.06f // subtle brightness, same style as Home
            )

            // Overlay content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Report Issue", style = MaterialTheme.typography.headlineLarge)

                // Auto-filled area from Profile
                Text("Area: ${userData.value?.area ?: "Not selected"}")

                // Dropdown for issue type
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = issueType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Issue Type (tap to choose)") },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        issueOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    issueType = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                // Description field
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Describe the issue") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary
                    )
                )

                // Submit button
                Button(
                    onClick = {
                        val area = userData.value?.area ?: "Unknown"
                        viewModel.addReport(issueType, description, area)
                        description = "" // clear after submit
                        expanded = true  // reopen dropdown after submit
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Submit Report")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Show submitted reports
                if (reports.value.isNotEmpty()) {
                    Text("Your Reports", style = MaterialTheme.typography.titleLarge)
                    reports.value.forEachIndexed { index, report ->
                        val bgColor = if (index % 2 == 0)
                            MaterialTheme.colorScheme.secondaryContainer
                        else
                            MaterialTheme.colorScheme.tertiaryContainer

                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = bgColor)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text("Reference: ${report.id}")
                                Text("Area: ${report.area}")
                                Text("Issue: ${report.issueType}")
                                Text("Details: ${report.description}")
                                Text("Status: ${report.status}") // always "Under Review"
                            }
                        }
                    }
                }
            }
        }
    }
}

/* PREVIEW */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReportScreenPreview() {
    A210986_SirNelson_Lab1Theme {
        val viewModel: TrashViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        ReportScreen(viewModel)
    }
}