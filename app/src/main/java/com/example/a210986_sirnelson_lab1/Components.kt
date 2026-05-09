package com.example.a210986_sirnelson_lab1

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.a210986_sirnelson_lab1.ui.theme.GreenPrimary

/* ======================================================
   ✅ NEXT COLLECTION CARD (FINAL POLISHED)
   ====================================================== */
@Composable
fun NextCollectionCard(
    snackbarHostState: SnackbarHostState,
    viewModel: TrashViewModel
) {

    val trashInfo = viewModel.trashInfo.observeAsState()
    var expanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val cornerRadius by animateDpAsState(
        targetValue = if (expanded) 20.dp else 12.dp,
        label = "corner"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .animateContentSize(),
        shape = RoundedCornerShape(cornerRadius),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            /* ✅ HEADER */
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.DateRange, contentDescription = null) // ✅ FIXED
                Spacer(Modifier.width(6.dp))
                Text(
                    "Next Collection",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            /* ✅ CONTENT */
            trashInfo.value?.let { info ->
                Text("📍 ${info.area}")
                Text("📅 ${info.nextCollectionDate}")
            } ?: Text("No collection info yet")

            /* ✅ EXPANDED CONTENT */
            if (expanded) {

                HorizontalDivider()

                Text("⏳ Reminder ready", fontWeight = FontWeight.Medium)

                Text(
                    "Keep your bin outside before 7:00 AM for collection.",
                    style = MaterialTheme.typography.bodySmall
                )

                Button(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("✅ Reminder set successfully")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary
                    )
                ) {
                    Icon(Icons.Default.Notifications, contentDescription = null)
                    Spacer(Modifier.width(6.dp))
                    Text("Set Reminder")
                }
            }
        }
    }
}

/* ======================================================
   ✅ BOTTOM NAVIGATION BAR (FINAL CLEAN VERSION)
   ====================================================== */
@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface, // ✅ better UI
        tonalElevation = 4.dp
    ) {

        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = currentRoute == "schedule",
            onClick = { navController.navigate("schedule") },
            icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
            label = { Text("Schedule") }
        )

        NavigationBarItem(
            selected = currentRoute == "report",
            onClick = { navController.navigate("report") },
            icon = { Icon(Icons.Default.Warning, null) },
            label = { Text("Report") }
        )

        NavigationBarItem(
            selected = currentRoute == "reminder",
            onClick = { navController.navigate("reminder") },
            icon = { Icon(Icons.Default.Notifications, null) },
            label = { Text("Reminder") }
        )

        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = { navController.navigate("profile") },
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Profile") }
        )
    }
}