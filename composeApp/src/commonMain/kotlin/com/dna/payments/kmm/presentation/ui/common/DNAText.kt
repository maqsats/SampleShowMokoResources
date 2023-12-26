package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.poppinsFontFamily
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource


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

@Composable
fun DNATextWithIcon(
    text: String,
    modifier: Modifier = Modifier,
    icon: ImageResource,
    secondIcon: ImageResource? = null,
    style: TextStyle = DnaTextStyle.Normal16,
    textColor: Color = style.color,
    backgroundColor: Color = style.background,
    textAlign: TextAlign = TextAlign.Start
) {
    Row(
        modifier = modifier.height(24.dp).background(
            color = backgroundColor,
            shape = RoundedCornerShape(8.dp)
        ).padding(horizontal = 8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = textColor,
            modifier = Modifier.height(14.dp).width(14.dp)
        )
        Text(
            text = text,
            style = style,
            color = textColor,
            modifier = modifier.padding(horizontal = 4.dp),
            fontFamily = poppinsFontFamily(),
            textAlign = textAlign
        )
        if (secondIcon != null) {
            Icon(
                painter = painterResource(secondIcon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.height(14.dp).width(14.dp)
            )
        }
    }
}

