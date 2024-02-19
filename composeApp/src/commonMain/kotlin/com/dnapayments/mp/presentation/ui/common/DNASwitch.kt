package com.dnapayments.mp.presentation.ui.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.dnapayments.mp.presentation.theme.darkGrey
import com.dnapayments.mp.presentation.theme.outlineGreenColor
import com.dnapayments.mp.presentation.theme.white

@Composable
fun DNASwitch(
    isChecked: Boolean = true,
    onChanged: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(isChecked) }

    Switch(
        checked = checked,
        onCheckedChange = {
//            checked = it
            onChanged(it)
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