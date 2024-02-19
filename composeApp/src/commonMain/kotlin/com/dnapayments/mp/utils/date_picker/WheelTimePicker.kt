package com.dnapayments.mp.utils.date_picker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.main_screens.ScreenName
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.green3
import com.dnapayments.mp.presentation.theme.labelColor
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.presentation.ui.common.DNAYellowButton
import com.dnapayments.mp.utils.date_picker.wheel_picker.SelectorOptions
import com.dnapayments.mp.utils.date_picker.wheel_picker.WheelView
import dev.icerock.moko.resources.compose.stringResource
import kotlin.math.abs


@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    offset: Int = 2,
    selectorEffectEnabled: Boolean = true,
    textSize: Int = 20,
    onTimeChanged: (Int, Int) -> Unit = { _, _ -> }
) {

    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(0) }

    val hours = mutableListOf<Int>().apply {
        for (hour in 0..11) {
            add(hour)
        }
    }

    val minutes = mutableListOf<Int>().apply {
        for (minute in 0..59) {
            add(minute)
        }
    }

    val fontSize = maxOf(13, minOf(19, textSize))

    Column(modifier) {
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {

            val height = (fontSize + 15).dp

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(2f))

                WheelView(
                    modifier = Modifier.weight(3f),
                    itemSize = DpSize(150.dp, height),
                    selection = 0,
                    itemCount = hours.size,
                    rowOffset = offset,
                    selectorOption = SelectorOptions().copy(
                        selectEffectEnabled = selectorEffectEnabled,
                        enabled = false
                    ),
                    onFocusItem = {
                        selectedHour = hours[it]
                    },
                    content = { index, size ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(Paddings.extraSmall))
                            DNAText(
                                text = if (hours[index] < 10) "0${hours[index]}" else "${hours[index]}",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(50.dp),
                                style = DnaTextStyle.Medium14.copy(
                                    color = if (abs(size) == 0) green3 else labelColor,
                                    fontSize = (fontSize + 5 - 5 * (abs(size) / 13)).sp
                                )
                            )
                            if (abs(size) == 0)
                                selectedHour = hours[index]
                            Spacer(modifier = Modifier.height(Paddings.extraSmall))
                        }
                    })

                DNAText(
                    modifier = Modifier.padding(top = Paddings.extraSmall),
                    text = ":",
                    style = DnaTextStyle.Medium20.copy(
                        color = green3,
                        fontSize = 23.sp
                    )
                )

                WheelView(
                    modifier = Modifier.weight(3f),
                    itemSize = DpSize(150.dp, height),
                    selection = 0,
                    itemCount = minutes.size,
                    rowOffset = offset,
                    selectorOption = SelectorOptions().copy(
                        selectEffectEnabled = selectorEffectEnabled,
                        enabled = false
                    ),
                    onFocusItem = {
                        selectedMinute = minutes[it]
                    },
                    content = { index, size ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(Paddings.extraSmall))
                            DNAText(
                                text = if (minutes[index] < 10) "0${minutes[index]}" else "${minutes[index]}",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(50.dp),
                                style = DnaTextStyle.Medium14.copy(
                                    color = if (abs(size) == 0) green3 else labelColor,
                                    fontSize = (fontSize + 5 - 5 * (abs(size) / 13)).sp
                                )
                            )
                            if (abs(size) == 0)
                                selectedMinute = minutes[index]
                            Spacer(modifier = Modifier.height(Paddings.extraSmall))
                        }
                    })

                Spacer(modifier = Modifier.weight(2f))
            }
        }

        DNAYellowButton(
            text = stringResource(MR.strings.apply),
            screenName = ScreenName.DATE_PICKER,
            onClick = {
                onTimeChanged(selectedHour, selectedMinute)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(Paddings.medium)
        )
    }
}