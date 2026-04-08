package com.example.a210986_sirnelson_lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a210986_sirnelson_lab1.ui.theme.A210986_SirNelson_Lab1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            A210986_SirNelson_Lab1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    TrashTrackHome()
                }
            }
        }
    }
}

@Composable
fun TrashTrackHome() {

    var userName by remember { mutableStateOf("") }
    var areaInput by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.trashbackground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.08f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            /* HEADER */
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEAF5EC))
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
                    Text(
                        "Majlis Perbandaran Kajang",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        "TrashTrack: Waste Collection Reminder",
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Welcome back, ${if (userName.isBlank()) "Resident" else userName}!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = userName,
                onValueChange = { userName = it },
                label = { Text("Enter your name") },
                leadingIcon = {
                    Icon(Icons.Filled.Person, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            /* AREA SEARCH */
            Text("Check Trash Collection Info", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = areaInput,
                onValueChange = { areaInput = it },
                label = { Text("Enter your area (e.g. Bandar Baru Bangi)") },
                leadingIcon = {
                    Icon(Icons.Filled.LocationOn, contentDescription = null)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    resultText = when {
                        areaInput.contains("Bangi", ignoreCase = true) ->
                            "📅 Wednesday\n⏰ 7:00 – 10:00 AM\n🔔 Reminder: Put trash outside before 7:00 AM."

                        areaInput.contains("Kajang", ignoreCase = true) ->
                            "📅 Friday\n⏰ 7:00 – 10:00 AM\n🔔 Reminder: Put trash outside before 7:00 AM."

                        areaInput.contains("Semenyih", ignoreCase = true) ->
                            "📅 Monday\n⏰ 7:30 – 10:30 AM\n🔔 Reminder: Put trash outside before 7:30 AM."

                        else ->
                            "⚠️ Area not found. Please check your spelling."
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("Check Collection Info", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (resultText.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFEAF5EC))
                        .padding(16.dp)
                ) {
                    Text(resultText)
                }
            }

            /* QUICK ACTIONS */
            Spacer(modifier = Modifier.height(24.dp))
            Text("Quick Actions", fontWeight = FontWeight.Bold, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                QuickBox("View Schedule", Icons.Filled.Home, Modifier.weight(1f))
                Spacer(modifier = Modifier.width(12.dp))
                QuickBox("Report Missed", Icons.Filled.List, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))

            /* NEXT COLLECTION */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEAF5EC))
                    .padding(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text("Next Collection", fontWeight = FontWeight.Bold)
                        Text("Friday")
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("This Week", fontWeight = FontWeight.Bold)
                        Text("2 Collections")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            /* REMINDERS */
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFEAF5EC))
                    .padding(16.dp)
            ) {
                Column {
                    Text("• Put trash outside before 7:00 AM")
                    Text("• Recycling pickup on Saturday")
                    Text("• Keep Kajang clean ♻️")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            /* BOTTOM NAV */
            BottomNavigationBar()
        }
    }
}

/* QUICK ACTION BOX */
@Composable
fun QuickBox(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier) {
    Box(
        modifier = modifier
            .background(Color(0xFFEAF5EC))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, contentDescription = title, tint = Color(0xFF2E7D32))
            Spacer(modifier = Modifier.height(6.dp))
            Text(title)
        }
    }
}

/* BOTTOM NAV */
@Composable
fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEAF5EC))
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        NavItem("Home", Icons.Filled.Home)
        NavItem("Schedule", Icons.Filled.List)
        NavItem("Report", Icons.Filled.List)
        NavItem("Profile", Icons.Filled.Person)
    }
}

@Composable
fun NavItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = title, tint = Color(0xFF2E7D32))
        Text(title, fontSize = 12.sp)
    }
}