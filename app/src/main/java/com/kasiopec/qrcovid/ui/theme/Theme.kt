package com.kasiopec.qrcovid.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    onPrimary = Color.White
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    onPrimary = Color.White,

    secondary = Teal200,
    secondaryVariant = Teal700,
    onSecondary = Color.Black,

    background = Color.White,
    onSurface = Color.Black

    /* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
)

private val LightNewColorPalette = lightColors(
    primary = BottomBar,
    primaryVariant = CoffeDark,
    onPrimary = GhostWhite,

    secondary = Cinnabar,
    secondaryVariant = KeppelLight,
    onSecondary = Color.Black,

    background = Color.White,
    onSurface = Color.Black
)
@Composable
fun QRCovidTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = LightNewColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}