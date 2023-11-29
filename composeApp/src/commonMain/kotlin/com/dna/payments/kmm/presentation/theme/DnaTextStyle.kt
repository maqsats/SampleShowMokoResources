package com.dna.payments.kmm.presentation.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object DnaTextStyle {

    val SemiBold16 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    )

    val SemiBold18 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    )

    val SemiBold20 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    )

    val SemiBold22 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp
    )

    val Normal18 = TextStyle(
        fontSize = 18.sp
    )

    val Normal21 = TextStyle(
        fontSize = 21.sp
    )

    val Normal14 = TextStyle(
        fontSize = 14.sp
    )

    val Normal16 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    )

    val Bold = TextStyle(
        fontWeight = FontWeight.Bold
    )

    val Bold16 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )

    val Bold18 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )

    val Bold20 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    val Bold22 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    )

    val Medium20 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    )

    val Medium16 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )

    val Medium36 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp
    )

    val WithAlpha = TextStyle(
        color = colorMainWithAlpha
    )

    val WithAlpha14 = TextStyle(
        color = greyColorAlpha,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )

    val WithAlpha16 = TextStyle(
        color = greyColorAlpha,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )

    val ExtraBold = TextStyle(
        fontWeight = FontWeight.ExtraBold
    )

    val ExtraBold22 = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 17.sp
    )

    val BoldRed = TextStyle(
        fontWeight = FontWeight.Bold,
        color = orange
    )

    val Red16 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        color = red
    )

    val Green16 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = greenButtonNotFilled
    )

    val GreenBold20 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = greenButtonNotFilled
    )

    val RedBold20 = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = red
    )

    val Green14 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = greenButtonNotFilled
    )

    val BackgroundGreen12 = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = greenButtonNotFilled,
        background = greenBackground
    )

    val BackgroundGrey12 = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = greyColorAlpha,
        background = greyColorBackground
    )
}
