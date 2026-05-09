package com.example.a210986_sirnelson_lab1

/* ✅ ALL IMPORTS AT TOP (VERY IMPORTANT ✅) */
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight

import kotlinx.coroutines.launch

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.a210986_sirnelson_lab1.ui.theme.*

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: TrashViewModel,
    onNavigate: (String) -> Unit = {}
) {

    val userData = viewModel.userData.observeAsState()
    val reports = viewModel.reportData.observeAsState(emptyList())

    var name by remember { mutableStateOf(userData.value?.name ?: "") }
    var area by remember { mutableStateOf(userData.value?.area ?: "") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { MPKJHeaderBar(subtitle = "Profile") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {

            /* ✅ BACKGROUND IMAGE */
            Image(
                painter = painterResource(id = R.drawable.trashbackground),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.05f
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
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

                /* ✅ TITLE */
                Text("Profile", style = MaterialTheme.typography.headlineLarge)

                /* ✅ NAME FIELD */
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Enter your name") },
                    modifier = Modifier.fillMaxWidth()
                )

                /* ✅ AREA FIELD (UNCHANGED ✅ FULL WIDTH) */
                OutlinedTextField(
                    value = area,
                    onValueChange = { area = it },
                    label = { Text("Enter your area") },
                    modifier = Modifier.fillMaxWidth()
                )

                /* ✅ SAVE BUTTON WITH VALIDATION */
                Button(
                    onClick = {
                        if (name.isBlank() || area.isBlank()) {
                            scope.launch {
                                snackbarHostState.showSnackbar("⚠️ Please fill all fields")
                            }
                        } else {
                            viewModel.setUserData(name, area)
                            scope.launch {
                                snackbarHostState.showSnackbar("✅ Profile saved successfully")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary
                    )
                ) {
                    Text("Save")
                }

                /* ✅ MENU ITEMS */
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
                        shape = RoundedCornerShape(14.dp),
                        onClick = { onNavigate(route) },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(icon, null)

                            Spacer(Modifier.width(12.dp))

                            Text(
                                title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                /* ✅ SAVED PROFILE */
                userData.value?.let {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(Modifier.padding(14.dp)) {
                            Text("Saved Name: ${it.name}")
                            Text("Saved Area: ${it.area}")
                        }
                    }
                }

                /* ✅ LAST REPORT */
                reports.value.lastOrNull()?.let { report ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(Modifier.padding(14.dp)) {

                            Text(
                                "Last Reported Issue",
                                fontWeight = FontWeight.Bold
                            )

                            Text("Type: ${report.issueType}")
                            Text("Details: ${report.description}")
                        }
                    }
                }
            }
        }
    }
}

/* ✅ PREVIEW */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    A210986_SirNelson_Lab1Theme {
        val nav = rememberNavController()
        val vm: TrashViewModel =
            androidx.lifecycle.viewmodel.compose.viewModel()

        ProfileScreen(nav, vm)
    }
}
