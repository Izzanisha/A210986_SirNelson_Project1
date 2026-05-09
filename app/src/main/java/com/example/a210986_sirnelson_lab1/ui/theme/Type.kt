package com.example.a210986_sirnelson_lab1.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.a210986_sirnelson_lab1.R

/* ======================================================
   ROBOTO FONT FAMILY (Government / Material official)
   ====================================================== */
val Roboto = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_medium, FontWeight.Medium),
    Font(R.font.roboto_bold, FontWeight.Bold)
)

/* ======================================================
   MATERIAL DESIGN TYPE SCALE (Unified Government Style)
   ====================================================== */
val Typography = Typography(

    // Large screen headings (e.g. "Reminders & Tracking")
    headlineLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp
    ),

    // Section titles (e.g. "Next Collection", "Upcoming Reminders")
    titleLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),

    // Primary body text (main content)
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    // Secondary body text (supporting info, area, dates)
    bodyMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    // Button text (e.g. "Clear All", "Add Test Notification")
    labelLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
)