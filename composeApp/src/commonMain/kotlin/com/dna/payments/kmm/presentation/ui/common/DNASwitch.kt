package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.dna.payments.kmm.presentation.theme.darkGrey
import com.dna.payments.kmm.presentation.theme.outlineGreenColor
import com.dna.payments.kmm.presentation.theme.white

@Composable
fun DNASwitch() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
        interactionSource = MutableInteractionSource(),
        colors = SwitchDefaults.colors(
            checkedThumbColor = white,
            checkedTrackColor = outlineGreenColor,
            uncheckedThumbColor = white,
            uncheckedTrackColor = darkGrey,
            uncheckedBorderColor = darkGrey,
            checkedBorderColor = outlineGreenColor,

        )
    )
}