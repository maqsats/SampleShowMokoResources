package daniel.avila.rnm.kmm.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import daniel.avila.rnm.kmm.MR
import dev.icerock.moko.resources.compose.asFont

private val DarkColorPalette = darkColors(
    primary = RED,
    primaryVariant = BLACK,
    secondary = GRAY,
    secondaryVariant = BACKGROUND_GRAY,
    background = BACKGROUND,
    surface = BLUE,
    onSurface = WHITE
)

private val LightColorPalette = lightColors(
    primary = RED,
    primaryVariant = BLACK,
    secondary = GRAY,
    secondaryVariant = BACKGROUND_GRAY,
    background = BACKGROUND,
    surface = BLUE,
    onSurface = WHITE,
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun TengeTodayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val openSansFamily = FontFamily(
        MR.fonts.Opensans.light.asFont(weight = FontWeight.Light)!!,
        MR.fonts.Opensans.regular.asFont(weight = FontWeight.Normal)!!,
        MR.fonts.Opensans.italic.asFont(weight = FontWeight.Normal, style = FontStyle.Italic)!!,
        MR.fonts.Opensans.medium.asFont(weight = FontWeight.Medium)!!,
        MR.fonts.Opensans.semibold.asFont(weight = FontWeight.SemiBold)!!,
        MR.fonts.Opensans.bold.asFont(weight = FontWeight.Bold)!!,
        MR.fonts.Opensans.extrabold.asFont(weight = FontWeight.ExtraBold)!!,
    )

    val typography = Typography(
        body1 = TextStyle(
            fontFamily = openSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        body2 = TextStyle(
            fontFamily = openSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        ),
        button = TextStyle(
            fontFamily = openSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        ),
        caption = TextStyle(
            fontFamily = openSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp
        ),
        h1 = TextStyle(
            fontFamily = openSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        ),
        h3 = TextStyle(
            fontFamily = openSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        h4 = TextStyle(
            fontFamily = openSansFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        ),
        h5 = TextStyle(
            fontFamily = openSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 8.sp
        ),
        h6 = TextStyle(
            fontFamily = openSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        subtitle2 = TextStyle(
            fontFamily = openSansFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp
        ),
        h2 = TextStyle(
            fontFamily = openSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        ),
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}