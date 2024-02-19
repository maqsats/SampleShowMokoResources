package com.dnapayments.mp.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.dnapayments.mp.MR
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.greenButtonNotFilled
import com.dnapayments.mp.utils.UiText
import com.dnapayments.mp.utils.extension.noRippleClickable
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.delay

@Composable
fun SuccessPopup(
    successMessage: UiText,
    showPopup: MutableState<Boolean>
) {
    if (!showPopup.value) return

    LaunchedEffect(key1 = Unit) {
        delay(3000)
        showPopup.value = false
    }

    Popup(
        alignment = Alignment.TopCenter,
        properties = PopupProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            focusable = true
        ),
        onDismissRequest = {
            showPopup.value = false
        },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 30.dp, vertical = 20.dp)
                .border(1.dp, greenButtonNotFilled, shape = RoundedCornerShape(10.dp))
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            Image(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight().noRippleClickable { showPopup.value = false },
                painter = painterResource(MR.images.ic_success),
                contentDescription = null
            )
            Column(
                Modifier.weight(1f).wrapContentHeight()
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                DNAText(
                    text = successMessage.getText(),
                    style = DnaTextStyle.Normal14,
                    modifier = Modifier
                        .wrapContentHeight()
                )
            }
        }
    }
}