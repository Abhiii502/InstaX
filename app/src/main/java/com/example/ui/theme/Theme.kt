package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MinimalColorScheme = lightColorScheme(
    primary = MinimalPrimary,
    onPrimary = MinimalOnPrimary,
    primaryContainer = MinimalPrimaryContainer,
    onPrimaryContainer = MinimalOnPrimaryContainer,
    secondary = MinimalSecondary,
    secondaryContainer = MinimalSecondaryContainer,
    onSecondaryContainer = MinimalOnSecondaryContainer,
    background = MinimalBg,
    onBackground = MinimalTextPrimary,
    surface = MinimalSurface,
    onSurface = MinimalTextPrimary,
    surfaceVariant = MinimalSurfaceVariant,
    onSurfaceVariant = MinimalTextSecondary,
    outline = MinimalBorder,
    outlineVariant = MinimalBorderSubtle,
    error = MinimalError,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MinimalColorScheme,
        typography = Typography,
        content = content
    )
}

