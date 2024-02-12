package com.dna.payments.kmm.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.MR
import dev.icerock.moko.resources.compose.asFont

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
    MaterialTheme(
        colorScheme = lightColorScheme(),
        shapes = AppShapes,
        content = content
    )
}