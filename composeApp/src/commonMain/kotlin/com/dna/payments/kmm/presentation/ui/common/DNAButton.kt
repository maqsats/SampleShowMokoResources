package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.backgroundBtnNotEnabled
import com.dna.payments.kmm.presentation.theme.black
import com.dna.payments.kmm.presentation.theme.greenButtonNotFilled
import com.dna.payments.kmm.presentation.theme.yellowButton
import com.dna.payments.kmm.utils.extension.noRippleClickable
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun DNAYellowButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = yellowButton,
            contentColor = black,
            disabledBackgroundColor = backgroundBtnNotEnabled
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        DNAText(
            text = text,
            style = DnaTextStyle.SemiBold18,
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Composable
fun DNAGreenBackButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(modifier = modifier.noRippleClickable {
        onClick()
    }, horizontalArrangement = Arrangement.Center) {
        Icon(
            painterResource(MR.images.ic_green_arrow_back),
            contentDescription = null,
            tint = greenButtonNotFilled
        )
        Text(
            text = text,
            style = DnaTextStyle.Green16,
            modifier = Modifier.padding(vertical = 4.dp).padding(start = 4.dp)
        )
    }
}