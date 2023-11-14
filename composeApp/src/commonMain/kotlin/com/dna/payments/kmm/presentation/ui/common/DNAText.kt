package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.poppinsFontFamily

@Composable
fun DNAText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = DnaTextStyle.Normal16
) {
    Text(
        text = text,
        style = style,
        modifier = modifier,
        fontFamily = poppinsFontFamily()
    )
}
