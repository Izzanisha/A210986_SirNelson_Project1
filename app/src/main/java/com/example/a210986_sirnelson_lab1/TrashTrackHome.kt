package com.example.a210986_sirnelson_lab1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.a210986_sirnelson_lab1.ui.theme.A210986_SirNelson_Lab1Theme
import com.example.a210986_sirnelson_lab1.ui.theme.GreenPrimary
import com.example.a210986_sirnelson_lab1.ui.theme.MPKJHeaderBar

// ✅ Ensure this import is present
import androidx.compose.material3.TextFieldDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashTrackHome(navController: NavHostController, viewModel: TrashViewModel) {
    var userName by remember { mutableStateOf("") }
    var areaInput by remember { mutableStateOf("") }
    var areaMessage by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    val mpkjAreas = listOf(
        "Kajang", "Bandar Baru Bangi", "Semenyih", "Balakong", "Cheras Batu 9",
        "Sungai Long", "Hulu Langat", "Taman Sri Jelok", "Taman Prima Saujana",
        "Taman Kajang Utama", "Taman Bukit Mewah", "Taman Perkasa",
        "Taman Pinggiran Delima", "Taman Wawasan"
    )

    Scaffold(
        topBar = { MPKJHeaderBar(subtitle = "Home") },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background) // ✅ pastel background
        ) {
            Image(
                painter = painterResource(id = R.drawable.trashbackground),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                alpha = 0.06f
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                /* WELCOME */
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "Welcome back, ${if (userName.isBlank()) "Resident" else userName}!",
                            style = MaterialTheme.typography.headlineSmall
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = userName,
                            onValueChange = { userName = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Your Name", style = MaterialTheme.typography.bodyMedium) },
                            leadingIcon = { Icon(Icons.Filled.Person, null) },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                focusedIndicatorColor = MaterialTheme.colorScheme.outline,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                cursorColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }

                /* CHECK COLLECTION AREA */
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("🗺 Check Collection Area", style = MaterialTheme.typography.titleMedium)

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = areaInput,
                            onValueChange = { areaInput = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Enter your area", style = MaterialTheme.typography.bodyMedium) },
                            leadingIcon = { Icon(Icons.Filled.LocationOn, null) },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                focusedIndicatorColor = MaterialTheme.colorScheme.outline,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                cursorColor = MaterialTheme.colorScheme.primary
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                areaMessage =
                                    if (mpkjAreas.any { areaInput.contains(it, ignoreCase = true) }) {
                                        "This area is covered under MPKJ waste collection services."
                                    } else {
                                        "This location is not currently covered by MPKJ."
                                    }

                                viewModel.setUserData(userName, areaInput)
                                viewModel.setTrashInfo(areaInput, "Next Monday")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GreenPrimary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            Text("Check Area", style = MaterialTheme.typography.labelLarge)
                        }

                        if (areaMessage.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(areaMessage, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                /* QUICK NAVIGATION */
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = { navController.navigate("schedule") }) { Text("View Schedule") }
                    Button(onClick = { navController.navigate("report") }) { Text("Report Issue") }
                }

                /* NEXT COLLECTION */
                NextCollectionCard(snackbarHostState, viewModel)

                /* DAILY ECO TIP */
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Daily Garbage Hygiene Tips", style = MaterialTheme.typography.titleMedium)
                        Text(
                            "• Tie garbage bags tightly\n" +
                                    "• Do not mix wet and dry waste\n" +
                                    "• Rinse bins regularly\n" +
                                    "• Sprinkle baking soda to reduce odour",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

/* PREVIEW */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TrashTrackPreview() {
    A210986_SirNelson_Lab1Theme {
        val navController = rememberNavController()
        val viewModel: TrashViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
        TrashTrackHome(navController, viewModel)
    }
}