package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Clean Minimalism Color Palette
val MinimalBg = Color(0xFFFEF7FF)
val MinimalSurface = Color(0xFFFFFFFF)
val MinimalSurfaceVariant = Color(0xFFF3EDF7)
val MinimalSurfaceContainer = Color(0xFFECE6F0)

val MinimalPrimary = Color(0xFF6750A4)
val MinimalOnPrimary = Color(0xFFFFFFFF)
val MinimalPrimaryContainer = Color(0xFFEADDFF)
val MinimalOnPrimaryContainer = Color(0xFF21005D)

val MinimalSecondary = Color(0xFF625B71)
val MinimalSecondaryContainer = Color(0xFFDDE1FF)
val MinimalOnSecondaryContainer = Color(0xFF1D192B)

val MinimalAccentCoral = Color(0xFFFFB4AB)
val MinimalError = Color(0xFFBA1A1A)

val MinimalTextPrimary = Color(0xFF1D1B20)
val MinimalTextSecondary = Color(0xFF49454F)
val MinimalTextMuted = Color(0xFF79747E)

val MinimalBorder = Color(0xFFCAC4D0)
val MinimalBorderSubtle = Color(0x33CAC4D0)
val MinimalDivider = Color(0xFFE7E0EC)

// Aliases for compatibility across components
val InstaBlack = MinimalBg
val InstaDarkGray = MinimalSurfaceVariant
val InstaCardBg = MinimalSurface
val InstaBorder = MinimalBorderSubtle
val InstaDivider = MinimalDivider

val InstaPink = MinimalPrimary
val InstaPurple = MinimalPrimary
val InstaOrange = MinimalAccentCoral
val InstaYellow = MinimalPrimaryContainer
val InstaRed = MinimalError
val InstaBlue = MinimalPrimary

val InstaTextPrimary = MinimalTextPrimary
val InstaTextSecondary = MinimalTextSecondary
val InstaTextMuted = MinimalTextMuted

// Minimalist rings and gradients
val MinimalStoryRing = Brush.sweepGradient(
    colors = listOf(
        Color(0xFF6750A4),
        Color(0xFFFFB4AB),
        Color(0xFFDDE1FF),
        Color(0xFF6750A4)
    )
)

val MinimalSeenStoryRing = Brush.linearGradient(
    colors = listOf(
        Color(0xFFCAC4D0),
        Color(0xFFE7E0EC)
    )
)

val InstaStoryGradient = MinimalStoryRing
val InstaSeenStoryGradient = MinimalSeenStoryRing

