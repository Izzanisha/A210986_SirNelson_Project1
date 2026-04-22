package com.example.a210986_sirnelson_lab1.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/* ======================================================
   LIGHT THEME – ALL GREEN (TRASH TRACKER / NATURE)
   ====================================================== */
private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = GreenOnPrimary,

    background = GreenBackground,
    onBackground = Color(0xFF1C1C1C),

    surface = GreenCard,              // ✅ cards default green
    onSurface = GreenOnCard
)

/* ======================================================
   DARK THEME – DARK GREEN
   ====================================================== */
private val DarkColorScheme = darkColorScheme(
    primary = GreenDarkPrimary,
    onPrimary = Color.White,

    background = GreenDarkBackground,
    onBackground = GreenDarkOn,

    surface = GreenDarkCard,
    onSurface = GreenDarkOn
)

/* ======================================================
   APP THEME
   ====================================================== */
@Composable
fun A210986_SirNelson_Lab1Theme(
    content: @Composable () -> Unit
) {
    val colors =
        if (isSystemInDarkTheme()) DarkColorScheme
        else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,   // ✅ Roboto typography from Type.kt
        content = content
    )
}