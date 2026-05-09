package com.example.a210986_sirnelson_lab1

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.example.a210986_sirnelson_lab1.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportScreen(navController: NavHostController, viewModel: TrashViewModel) {

    val userData = viewModel.userData.observeAsState()
    val reports = viewModel.reportData.observeAsState(emptyList())

    val issueOptions = listOf(
        "Missed Collection",
        "Late Collection",
        "Damaged Bin",
        "Overflowing Bin",
        "Uncollected Street Waste",
        "Other"
    )

    var issueType by remember { mutableStateOf(issueOptions.first()) }
    var expanded by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { MPKJHeaderBar(subtitle = "Report Issue") },
        bottomBar = { BottomNavigationBar(navController) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            /* ✅ BACK BUTTON */
            Row(
                modifier = Modifier.clickable {
                    navController.navigate("home")
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text("Back", style = MaterialTheme.typography.bodyLarge)
            }

            /* ✅ TITLE */
            Text(
                "🚨 Report Issue",
                style = MaterialTheme.typography.headlineLarge
            )

            /* ✅ AREA */
            Text(
                "📍 Area: ${userData.value?.area ?: "Not selected"}",
                style = MaterialTheme.typography.bodyLarge
            )

            /* ✅ ISSUE TYPE CARD */
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(3.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {

                Column(Modifier.padding(16.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, null)
                        Spacer(Modifier.width(6.dp))
                        Text("Issue Type", fontWeight = FontWeight.Bold)
                    }

                    Spacer(Modifier.height(8.dp))

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {

                        OutlinedTextField(
                            value = issueType,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Select issue type") },
                            modifier = Modifier.fillMaxWidth().menuAnchor(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            issueOptions.forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        issueType = it
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            /* ✅ DESCRIPTION CARD */
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(3.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {

                Column(Modifier.padding(16.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Edit, null)
                        Spacer(Modifier.width(6.dp))
                        Text("Description", fontWeight = FontWeight.Bold)
                    }

                    Spacer(Modifier.height(8.dp))

                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Describe the issue") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                }
            }

            /* ✅ SUBMIT BUTTON */
            Button(
                onClick = {
                    val area = userData.value?.area ?: "Unknown"

                    viewModel.addReport(issueType, description, area)

                    description = ""
                    issueType = issueOptions.first()

                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("✔️ Report submitted successfully")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                Text("Submit Report")
            }

            /* ✅ REPORT HISTORY */
            if (reports.value.isNotEmpty()) {

                Text(
                    "📄 Your Reports",
                    style = MaterialTheme.typography.titleLarge
                )

                reports.value.forEach { report ->

                    val statusColor = when (report.status) {
                        "Under Review" -> androidx.compose.ui.graphics.Color(0xFFFFA000)
                        "Resolved" -> androidx.compose.ui.graphics.Color(0xFF2E7D32)
                        else -> MaterialTheme.colorScheme.primary
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {

                        Column(Modifier.padding(12.dp)) {

                            Text("Ref: ${report.id}", fontWeight = FontWeight.Bold)

                            Text("Area: ${report.area}")
                            Text("Type: ${report.issueType}")
                            Text("Details: ${report.description}")

                            Spacer(Modifier.height(4.dp))

                            Text(
                                "Status: ${report.status}",
                                color = statusColor,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

/* ✅ PREVIEW */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReportScreenPreview() {

    A210986_SirNelson_Lab1Theme {

        val navController = rememberNavController()
        val viewModel: TrashViewModel =
            androidx.lifecycle.viewmodel.compose.viewModel()

        ReportScreen(navController, viewModel)
    }
}