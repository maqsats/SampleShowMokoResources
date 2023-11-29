package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.red
import com.dna.payments.kmm.utils.UiText
import com.dna.payments.kmm.utils.extension.noRippleClickable
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.delay

@Composable
fun ErrorPopup(
    onSupportScreenClick: () -> Unit = {},
    error: UiText,
    showPopup: MutableState<Boolean>
) {
    if (!showPopup.value) return

    LaunchedEffect(key1 = Unit) {
        delay(5000)
        showPopup.value = false
    }

    Popup(
        alignment = Alignment.TopCenter,
        properties = PopupProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = true,
            focusable = true
        ),
        onDismissRequest = {
            showPopup.value = false
        },
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight().widthIn(min = 300.dp, max = 400.dp)
                .padding(horizontal = 30.dp, vertical = 20.dp)
                .border(1.dp, red, shape = RoundedCornerShape(10.dp))
                .background(Color.White, shape = RoundedCornerShape(10.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Image(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight().noRippleClickable { showPopup.value = false },
                painter = painterResource(MR.images.arrow_close),
                contentDescription = null
            )
            Column(
                Modifier.weight(1f).wrapContentHeight()
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                DNAText(
                    text = error.getText(),
                    style = DnaTextStyle.Normal14,
                    modifier = Modifier
                        .wrapContentHeight()
                )
                DNAText(
                    text = stringResource(MR.strings.our_support_team_to_help),
                    style = DnaTextStyle.Normal14,
                    modifier = Modifier
                        .wrapContentHeight()
                )
                DNAText(
                    text = stringResource(MR.strings.support),
                    style = DnaTextStyle.Green14,
                    modifier = Modifier
                        .wrapContentHeight().noRippleClickable(onSupportScreenClick)
                )
            }
        }
    }
}