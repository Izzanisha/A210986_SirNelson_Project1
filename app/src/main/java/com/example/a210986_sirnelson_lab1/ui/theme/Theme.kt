package com.example.a210986_sirnelson_lab1.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/* ======================================================
   LIGHT THEME – Pastel Green (Trash Tracker / Nature)
   ====================================================== */
private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = GreenOnPrimary,

    background = GreenBackground,
    onBackground = Color.Black,

    surface = GreenPastel,
    onSurface = GreenOnCard,

    surfaceVariant = GreenPastel,
    onSurfaceVariant = GreenOnCard,

    secondary = GreenPastel,
    onSecondary = GreenOnCard,
    secondaryContainer = GreenPastel,
    onSecondaryContainer = GreenOnCard,

    tertiary = GreenPastel,
    onTertiary = GreenOnCard,
    tertiaryContainer = GreenPastel,
    onTertiaryContainer = GreenOnCard,

    outline = GreenOnCard, // ✅ borders for text fields/dividers

    error = Color(0xFFB00020),
    onError = Color.White,
    errorContainer = Color(0xFFF2B8B5),
    onErrorContainer = Color.Black
)

/* ======================================================
   DARK THEME – Dark Green
   ====================================================== */
private val DarkColorScheme = darkColorScheme(
    primary = GreenDarkPrimary,
    onPrimary = Color.White,

    background = GreenDarkBackground,
    onBackground = GreenDarkOn,

    surface = GreenDarkCard,
    onSurface = GreenDarkOn,

    surfaceVariant = GreenDarkCard,
    onSurfaceVariant = GreenDarkOn,

    secondary = GreenDarkCard,
    onSecondary = GreenDarkOn,
    secondaryContainer = GreenDarkCard,
    onSecondaryContainer = GreenDarkOn,

    tertiary = GreenDarkCard,
    onTertiary = GreenDarkOn,
    tertiaryContainer = GreenDarkCard,
    onTertiaryContainer = GreenDarkOn,

    outline = GreenDarkOn, // ✅ borders

    error = Color(0xFFCF6679),
    onError = Color.Black,
    errorContainer = Color(0xFF8C1D18),
    onErrorContainer = Color.White
)

/* ======================================================
   APP THEME
   ====================================================== */
@Composable
fun A210986_SirNelson_Lab1Theme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,   // ✅ Roboto typography from Type.kt
        content = content
    )
}