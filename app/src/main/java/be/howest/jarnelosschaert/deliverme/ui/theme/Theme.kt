package be.howest.jarnelosschaert.deliverme.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Blue,
    onPrimary = White,
    //primaryVariant = Purple700,
    secondary = DarkBlue,
    onSecondary = DarkGrey,
    background = White,
    onBackground = Black,
    surface = Blue,
    onSurface = White,
)

private val LightColorPalette = lightColors(
    primary = Blue,
    onPrimary = White,
    //primaryVariant = Purple700,
    secondary = DarkBlue,
    onSecondary = DarkGrey,
    background = White,
    onBackground = Black,
    surface = Blue,
    onSurface = White,

    /* Other default colors to override
    surface = Color.White,
    onSecondary = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun DeliverMeTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
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