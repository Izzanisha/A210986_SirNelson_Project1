package com.example.a210986_sirnelson_lab1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a210986_sirnelson_lab1.ui.theme.A210986_SirNelson_Lab1Theme
import com.example.a210986_sirnelson_lab1.R

@Composable
fun ScheduleScreen(viewModel: TrashViewModel) {
    val trashInfo = viewModel.trashInfo.observeAsState()
    val reports = viewModel.reportData.observeAsState(emptyList()) // list of reports

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // 🔑 Bright background image (same style as Home)
            Image(
                painter = painterResource(id = R.drawable.trashbackground),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.06f // keep it subtle and bright
            )

            // Overlay content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 🔑 Main heading (bold, large)
                Text("Upcoming Collection Schedule", style = MaterialTheme.typography.headlineLarge)

                // Show area + next collection date
                trashInfo.value?.let { info ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("📍 Area: ${info.area}", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Text("📅 Next Collection Date: ${info.nextCollectionDate}", color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Text("ℹ️ Please place bins outside by 6AM", color = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    }
                } ?: Text("No area selected yet.")

                Spacer(modifier = Modifier.height(16.dp))

                // ✅ Weekly waste type schedule
                Text("Weekly Waste Collection", style = MaterialTheme.typography.titleLarge)

                val schedule = listOf(
                    Triple("Monday", "General Waste", "Food scraps, packaging, diapers"),
                    Triple("Wednesday", "Recycling", "Paper, cardboard, bottles, cans, glass"),
                    Triple("Friday", "Garden & Bulky Waste", "Grass clippings, branches, old furniture")
                )

                schedule.forEachIndexed { index, (day, type, examples) ->
                    val bgColor = if (index % 2 == 0) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.tertiaryContainer
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = bgColor)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("📅 $day", style = MaterialTheme.typography.titleMedium)
                            Text("Type: $type")
                            Text("Examples: $examples")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Show reported issues if available
                if (reports.value.isNotEmpty()) {
                    Text("Reported Issues", style = MaterialTheme.typography.titleLarge)
                    reports.value.forEachIndexed { index, report ->
                        val bgColor = if (index % 2 == 0) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.tertiaryContainer
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = bgColor)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text("⚠️ Report #${index + 1}", style = MaterialTheme.typography.titleMedium)
                                Text("Type: ${report.issueType}")
                                Text("Details: ${report.description}")
                                Text("Status: ${report.status}")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ✅ Contact info card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("📞 Contact MPKJ", style = MaterialTheme.typography.titleLarge)
                        Text("Address: Majlis Perbandaran Kajang, Menara MPKj Jalan Cempaka Putih, Off Jalan Semenyih, 43000 Kajang, Selangor")
                        Text("Main Line: 03-87377899 / 87330798")
                        Text("Fax: 03-87377897")
                        Text("Hotline: 1-800-88-6755")
                        Text("Email (complaints): aduan@mpkj.gov.my")
                        Text("Portal feedback: webmaster@mpkj.gov.my")
                    }
                }
            }
        }
    }
}

/* PREVIEW */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScheduleScreenPreview() {
    A210986_SirNelson_Lab1Theme {
        val viewModel: TrashViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        ScheduleScreen(viewModel)
    }
}