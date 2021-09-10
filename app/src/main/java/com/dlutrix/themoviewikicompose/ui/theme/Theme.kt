package com.dlutrix.themoviewikicompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Teal,
    primaryVariant = Teal,
    secondary = Teal,
    secondaryVariant = Teal,
    onPrimary = GainsBoro,
    onSecondary = GainsBoro,
    background = Black,
    surface = BlackShark,
    onBackground = Silver,
    onSurface = Silver,
    onError = Maroon
)

private val LightColorPalette = lightColors(
    primary = Teal,
    primaryVariant = Teal,
    secondary = Teal,
    secondaryVariant = Teal,
    onPrimary = GainsBoro,
    onSecondary = GainsBoro,
    background = WhiteSmoke,
    surface = White,
    onBackground = DarkSlateGray,
    onSurface = DarkSlateGray,
    onError = Maroon
)

@Composable
fun TheMovieWikiComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}