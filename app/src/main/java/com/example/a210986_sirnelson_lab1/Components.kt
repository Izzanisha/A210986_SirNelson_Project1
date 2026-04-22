package com.example.a210986_sirnelson_lab1

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.a210986_sirnelson_lab1.ui.theme.*

/* ======================================================
   NEXT COLLECTION CARD (TASK 3 + SHAPE MORPH)
   ====================================================== */

@Composable
fun NextCollectionCard(
    snackbarHostState: SnackbarHostState
) {
    var expanded by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    /* SHAPE MORPH */
    val cornerRadius by animateDpAsState(
        targetValue = if (expanded) 28.dp else 12.dp,
        label = "CardCorner"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .animateContentSize(),
        shape = RoundedCornerShape(cornerRadius),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = GreenCard,
            contentColor = GreenOnCard
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                /* FIXED ICON */
                Icon(Icons.Filled.Delete, contentDescription = null)

                Column {
                    Text(
                        text = "Next Collection",
                        fontWeight = FontWeight.Bold,
                        color = GreenPrimary
                    )
                    Text(
                        text = "Friday, 17 April 2026",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            if (expanded) {

                /* ✅ FIXED DIVIDER */
                HorizontalDivider(
                    thickness = 1.dp,
                    color = GreenPrimary.copy(alpha = 0.4f)
                )

                Text(
                    text = "⏳ 4 days remaining",
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "Please place your garbage bin in front of your house before 7:00 AM for MPKJ collection."
                )

                Button(
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = "✅ Reminder successfully set"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GreenPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Filled.Notifications, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Set Reminder")
                }
            }
        }
    }
}

/* ======================================================
   BOTTOM NAVIGATION (ALL GREEN)
   ====================================================== */

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = GreenCard,
        tonalElevation = 4.dp
    ) {

        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Filled.Home, null) },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = GreenPrimary,
                selectedTextColor = GreenPrimary,
                indicatorColor = GreenPrimary.copy(alpha = 0.2f)
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Filled.List, null) },
            label = { Text("Schedule") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Filled.Warning, null) },
            label = { Text("Report") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Filled.Person, null) },
            label = { Text("Profile") }
        )
    }
}