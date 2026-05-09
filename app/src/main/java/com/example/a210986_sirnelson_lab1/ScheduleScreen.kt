package com.example.a210986_sirnelson_lab1

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack

import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview

import com.example.a210986_sirnelson_lab1.ui.theme.*

/* =========================
   NEXT PICKUP LOGIC
   ========================= */
fun getNextPickupData(): Triple<String, String, Long> {

    val calendar = Calendar.getInstance()
    val today = calendar.get(Calendar.DAY_OF_WEEK)

    val schedule = listOf(
        Calendar.MONDAY to "General Waste",
        Calendar.WEDNESDAY to "Recycling",
        Calendar.FRIDAY to "Garden & Bulky"
    )

    var minDiff = 7
    var nextType = ""

    for ((day, type) in schedule) {
        var diff = day - today
        if (diff <= 0) diff += 7

        if (diff < minDiff) {
            minDiff = diff
            nextType = type
        }
    }

    calendar.add(Calendar.DAY_OF_MONTH, minDiff)

    val format = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val date = format.format(calendar.time)

    return Triple(date, nextType, minDiff.toLong())
}

/* =========================
   COUNTDOWN
   ========================= */
@Composable
fun CountdownText(daysLeft: Long) {

    var seconds by remember { mutableLongStateOf(daysLeft * 86400) }

    LaunchedEffect(Unit) {
        while (seconds > 0) {
            delay(1000)
            seconds--
        }
    }

    val days = seconds / 86400
    val text = if (days == 1L) "Tomorrow" else "In $days days"

    Text(text, fontWeight = FontWeight.SemiBold)
}

/* =========================
   NEXT PICKUP CARD
   ========================= */
@Composable
fun AnimatedNextCard(area: String, type: String, date: String, daysLeft: Long) {

    var expanded by remember { mutableStateOf(false) }

    val radius by animateDpAsState(
        targetValue = if (expanded) 28.dp else 16.dp,
        label = "corner"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .animateContentSize(),
        shape = RoundedCornerShape(radius),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {

        Column(Modifier.padding(16.dp)) {

            Text("Next Pickup", fontWeight = FontWeight.Bold)

            Text(type)
            Text(date)

            CountdownText(daysLeft)

            Text("📍 $area")

            if (expanded) {
                Divider()
                Text("Put bins outside before 6 AM")
                Text("Check Reminder tab for alerts")
            }
        }
    }
}

/* =========================
   MAIN SCREEN
   ========================= */
@Composable
fun ScheduleScreen(navController: NavHostController, viewModel: TrashViewModel) {

    val userData = viewModel.userData.observeAsState()
    val reports = viewModel.reportData.observeAsState(emptyList())

    val area = userData.value?.area ?: "Not selected"

    val (nextDate, nextType, daysLeft) = getNextPickupData()

    LaunchedEffect(nextDate, nextType) {
        viewModel.syncReminderWithSchedule(nextType, nextDate)
    }

    Scaffold(
        topBar = { MPKJHeaderBar("Collection Schedule") },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            /* ✅ BACK BUTTON */
            Row(
                modifier = Modifier.clickable {
                    navController.navigate("home")
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                Spacer(Modifier.width(8.dp))
                Text("Back")
            }

            Text("📅 Collection Schedule", style = MaterialTheme.typography.headlineSmall)

            /* ✅ NEXT PICKUP */
            AnimatedNextCard(area, nextType, nextDate, daysLeft)

            /* ✅ WEEKLY SCHEDULE (ALL GREEN NOW ✅) */
            Text("♻️ Weekly Schedule", style = MaterialTheme.typography.titleMedium)

            val weekly = listOf(
                "Monday" to "General Waste",
                "Wednesday" to "Recycling",
                "Friday" to "Garden & Bulky"
            )

            weekly.forEach { (day, type) ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {

                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(Icons.Default.Delete, null)

                        Spacer(Modifier.width(12.dp))

                        Column {
                            Text(day, fontWeight = FontWeight.Bold)
                            Text(type)
                        }
                    }
                }
            }

            /* ✅ ✅ REPORT SECTION */
            if (reports.value.isNotEmpty()) {

                Text("🚨 Reported Issues", style = MaterialTheme.typography.titleMedium)

                reports.value.forEach { report ->

                    val color = when (report.status) {
                        "Under Review" -> Color(0xFFFFA000)
                        "Resolved" -> Color(0xFF2E7D32)
                        else -> MaterialTheme.colorScheme.primary
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {

                        Column(Modifier.padding(14.dp)) {
                            Text(report.issueType, fontWeight = FontWeight.Bold)
                            Text(report.description)
                            Text(report.status, color = color)
                        }
                    }
                }
            }

            /* ✅ CONTACT CARD */
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Text("MPKJ Contact", fontWeight = FontWeight.Bold)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Phone, null)
                        Spacer(Modifier.width(12.dp))
                        Text("1-800-88-6755")
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Email, null)
                        Spacer(Modifier.width(12.dp))
                        Text("aduan@mpkj.gov.my")
                    }
                }
            }
        }
    }
}

/* ✅ PREVIEW */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSchedule() {
    A210986_SirNelson_Lab1Theme {
        val nav = rememberNavController()
        val vm: TrashViewModel =
            androidx.lifecycle.viewmodel.compose.viewModel()

        ScheduleScreen(nav, vm)
    }
}