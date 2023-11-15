package com.dna.payments.kmm.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.MR
import dev.icerock.moko.resources.compose.asFont

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    error = md_theme_light_error,
    onError = md_theme_light_onError,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = white,
    onSurface = md_theme_light_onSurface,
)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    error = md_theme_dark_error,
    onError = md_theme_dark_onError,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = white,
    onSurface = md_theme_dark_onSurface,
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun poppinsFontFamily() = FontFamily(
    MR.fonts.Poppins.light.asFont(weight = FontWeight.Light)!!,
    MR.fonts.Poppins.regular.asFont(weight = FontWeight.Normal)!!,
    MR.fonts.Poppins.italic.asFont(weight = FontWeight.Normal, style = FontStyle.Italic)!!,
    MR.fonts.Poppins.medium.asFont(weight = FontWeight.Medium)!!,
    MR.fonts.Poppins.semiBold.asFont(weight = FontWeight.SemiBold)!!,
    MR.fonts.Poppins.bold.asFont(weight = FontWeight.Bold)!!,
    MR.fonts.Poppins.extraBold.asFont(weight = FontWeight.ExtraBold)!!,
)

@Composable
internal fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!useDarkTheme) {
        LightColorScheme
    } else {
        DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colors,
        shapes = AppShapes,
        content = {
            Surface(content = content)
        }
    )
}