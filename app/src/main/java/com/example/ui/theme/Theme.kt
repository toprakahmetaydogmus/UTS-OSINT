package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val CyberColorScheme = darkColorScheme(
    primary = CyberCyan,
    onPrimary = CyberBlack,
    secondary = CyberGreen,
    onSecondary = CyberBlack,
    tertiary = CyberBlue,
    background = CyberBlack,
    onBackground = CyberWhite,
    surface = CyberNavy,
    onSurface = CyberWhite,
    surfaceVariant = CyberBorder,
    onSurfaceVariant = CyberGray,
    outline = CyberBorder,
    error = CyberRed,
    onError = CyberWhite
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // Force immersive dark mode for cyber tactical feeling
    dynamicColor: Boolean = false, // Disable device dynamic theme to keep the unique tactical cyber theme
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CyberColorScheme,
        typography = Typography,
        content = content
    )
}
