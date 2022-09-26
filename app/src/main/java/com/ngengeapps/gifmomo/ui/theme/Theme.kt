package com.ngengeapps.gifmomo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val YellowThemeLight = lightColors(
    primary = yellow500,
    primaryVariant = yellow400,
    onPrimary = Color.Black,
    secondary = blue700,
    secondaryVariant = blue800,
    onSecondary = Color.White,
)

private val YellowThemeDark = darkColors(
    primary = yellow200,
    secondary = blue200,
    onSecondary = Color.Black,
    surface = yellowDarkPrimary,
)


@Composable
fun GifMoMoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) YellowThemeDark else YellowThemeLight
    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}