package com.example.questify.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Cores customizadas expostas via CompositionLocal p/ preservar Color("Cream") etc
data class QuestifyColors(
    val lightBrown: Color,
    val darkBrown: Color,
    val cream: Color,
    val beige: Color,
)

val LocalQuestifyColors = staticCompositionLocalOf {
    QuestifyColors(LightBrown, DarkBrown, Cream, Beige)
}

private val LightColorSchemeQ = lightColorScheme(
    primary = DarkBrown,
    onPrimary = Cream,
    secondary = Beige,
    onSecondary = DarkBrown,
    background = LightBrown,
    onBackground = DarkBrown,
    surface = Cream,
    onSurface = DarkBrown,
    surfaceVariant = Beige,
    onSurfaceVariant = DarkBrown,
)

private val DarkColorSchemeQ = darkColorScheme(
    primary = DarkBrownLight,
    onPrimary = CreamDark,
    secondary = BeigeDark,
    onSecondary = DarkBrownLight,
    background = LightBrownDark,
    onBackground = DarkBrownLight,
    surface = CreamDark,
    onSurface = DarkBrownLight,
    surfaceVariant = BeigeDark,
    onSurfaceVariant = DarkBrownLight,
)

private val LightPalette = QuestifyColors(LightBrown, DarkBrown, Cream, Beige)
private val DarkPalette  = QuestifyColors(LightBrownDark, DarkBrownLight, CreamDark, BeigeDark)

@Composable
fun QuestifyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorSchemeQ else LightColorSchemeQ
    val palette = if (darkTheme) DarkPalette else LightPalette

    CompositionLocalProvider(LocalQuestifyColors provides palette) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content,
        )
    }
}
