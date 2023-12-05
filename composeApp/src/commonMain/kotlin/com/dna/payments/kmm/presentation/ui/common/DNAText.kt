package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.poppinsFontFamily

@Composable
fun DNAText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = DnaTextStyle.Normal16,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int? = null
) {
    Text(
        text = text,
        style = style,
        modifier = modifier,
        fontFamily = poppinsFontFamily(),
        textAlign = textAlign,
        maxLines = maxLines ?: Int.MAX_VALUE,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun DNATextWithBackground(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = DnaTextStyle.Normal16,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        style = style,
        modifier = modifier.background(
            color = style.background,
            shape = RoundedCornerShape(100.dp)
        ).padding(horizontal = 6.dp),
        fontFamily = poppinsFontFamily(),
        textAlign = textAlign
    )
}
