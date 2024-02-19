package com.dnapayments.mp.utils.date_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.dnapayments.mp.presentation.theme.Dimens
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.timeSwitchBg
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.utils.extension.noRippleClickable

@Composable
fun TimeInput(
    state: TimeInputState,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(Dimens.switchTimeHeight)
            .clip(RoundedCornerShape(Paddings.small))
            .background(timeSwitchBg)
            .noRippleClickable(onClick)
            .padding(vertical = Paddings.xxSmall, horizontal = Paddings.standard),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        DNAText(
            "${if (state.hour < 10) "0" else ""}${state.hour}:${if (state.minute < 10) "0" else ""}${state.minute}",
            style = DnaTextStyle.Normal20
        )
    }
}

data class TimeInputState(
    val hour: Int,
    val minute: Int
)
