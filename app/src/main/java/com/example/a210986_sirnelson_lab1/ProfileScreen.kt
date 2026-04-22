package com.example.a210986_sirnelson_lab1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.a210986_sirnelson_lab1.ui.theme.A210986_SirNelson_Lab1Theme
import com.example.a210986_sirnelson_lab1.R

@Composable
fun ProfileScreen(viewModel: TrashViewModel, onNavigate: (String) -> Unit = {}) {
    val userData = viewModel.userData.observeAsState()
    val reports = viewModel.reportData.observeAsState(emptyList())

    var name by remember { mutableStateOf(userData.value?.name ?: "") }
    var area by remember { mutableStateOf(userData.value?.area ?: "") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
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
                alpha = 0.06f // subtle brightness, same style as Home & Schedule
            )

            // Overlay content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Profile", style = MaterialTheme.typography.headlineLarge)

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Enter your name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = area,
                    onValueChange = { area = it },
                    label = { Text("Enter your area") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        viewModel.setUserData(name, area)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("✔️ Profile updated successfully")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ✅ Menu items inside Profile
                val menuItems = listOf(
                    Triple("Name", Icons.Default.Person, "profile_name"),
                    Triple("My Area", Icons.Default.Home, "profile_area"),
                    Triple("Next Collection Date", Icons.Default.DateRange, "schedule"),
                    Triple("Nearest Bin Location", Icons.Default.LocationOn, "map"),
                    Triple("Alerts & Notices", Icons.Default.Notifications, "alerts"),
                    Triple("Report a Problem", Icons.Default.Warning, "report"),
                    Triple("Settings", Icons.Default.Settings, "settings")
                )

                menuItems.forEach { (title, icon, route) ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        onClick = { onNavigate(route) }
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(icon, contentDescription = title)
                            Text(title, style = MaterialTheme.typography.titleMedium)
                        }
                    }
                }

                // ✅ Show saved profile info
                userData.value?.let {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Saved Name: ${it.name}", style = MaterialTheme.typography.bodyLarge)
                            Text("Saved Area: ${it.area}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }

                // ✅ Show last submitted report
                reports.value.lastOrNull()?.let { report ->
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Last Reported Issue", style = MaterialTheme.typography.titleLarge)
                            Text("Type: ${report.issueType}")
                            Text("Details: ${report.description}")
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
fun ProfileScreenPreview() {
    A210986_SirNelson_Lab1Theme {
        val viewModel: TrashViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        ProfileScreen(viewModel)
    }
}