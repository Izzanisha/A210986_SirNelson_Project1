package com.example.a210986_sirnelson_lab1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.a210986_sirnelson_lab1.ui.theme.*

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
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Background image
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                /* HEADER */
                Card {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mpkj),
                            contentDescription = null,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text("Majlis Perbandaran Kajang (MPKJ)", fontWeight = FontWeight.Bold)
                            Text("Trash Tracker App • Eco Reminder", fontSize = 12.sp)
                        }
                    }
                }

                /* WELCOME */
                Card {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "Welcome back, ${if (userName.isBlank()) "Resident" else userName}!",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = userName,
                            onValueChange = { userName = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Your Name") },
                            leadingIcon = { Icon(Icons.Filled.Person, null) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedBorderColor = GreenPrimary,
                                unfocusedBorderColor = GreenPrimary,
                                cursorColor = GreenPrimary
                            )
                        )
                    }
                }

                /* CHECK COLLECTION AREA */
                Card {
                    Column(Modifier.padding(16.dp)) {
                        Text("🗺 Check Collection Area", fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = areaInput,
                            onValueChange = { areaInput = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Enter your area") },
                            leadingIcon = { Icon(Icons.Filled.LocationOn, null) },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedBorderColor = GreenPrimary,
                                unfocusedBorderColor = GreenPrimary,
                                cursorColor = GreenPrimary
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

                                // Save into ViewModel for other screens
                                viewModel.setUserData(userName, areaInput)
                                viewModel.setTrashInfo(areaInput, "Next Monday") // Example date
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GreenPrimary,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Check Area")
                        }

                        if (areaMessage.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(areaMessage)
                        }
                    }
                }

                /* QUICK NAVIGATION */
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = { navController.navigate("schedule") }) { Text("View Schedule") }
                    Button(onClick = { navController.navigate("report") }) { Text("Report Issue") }
                }

                /* NEXT COLLECTION */
                NextCollectionCard(snackbarHostState)

                /* DAILY ECO TIP */
                Card {
                    Column(Modifier.padding(16.dp)) {
                        Text("Daily Garbage Hygiene Tips", fontWeight = FontWeight.Bold)
                        Text(
                            "• Tie garbage bags tightly\n" +
                                    "• Do not mix wet and dry waste\n" +
                                    "• Rinse bins regularly\n" +
                                    "• Sprinkle baking soda to reduce odour"
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