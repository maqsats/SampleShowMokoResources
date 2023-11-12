package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.dna.payments.kmm.presentation.theme.DnaTextStyle

@Composable
fun DNAText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = DnaTextStyle.Normal16
) {
    Text(
        text = text,
        style = DnaTextStyle.Normal16,
        modifier = modifier,
//        fontFamily = updatedFontFamily,
    )
}
