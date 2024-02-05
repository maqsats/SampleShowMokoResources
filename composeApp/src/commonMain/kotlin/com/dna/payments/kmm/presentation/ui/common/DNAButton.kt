package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.main_screens.ScreenName
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.backgroundBtnNotEnabled
import com.dna.payments.kmm.presentation.theme.black
import com.dna.payments.kmm.presentation.theme.greenButtonNotFilled
import com.dna.payments.kmm.presentation.theme.yellowButton
import com.dna.payments.kmm.utils.constants.Constants.BUTTON_CLICK_EVENT
import com.dna.payments.kmm.utils.constants.Constants.BUTTON_NAME
import com.dna.payments.kmm.utils.constants.Constants.SCREEN_NAME
import com.dna.payments.kmm.utils.constants.Constants.delayInMillis
import com.dna.payments.kmm.utils.constants.Constants.initialTime
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.firebase.logEvent
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.delay

@Composable
fun DNAYellowButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    screenName: ScreenName,
    enabled: Boolean = true,
    icon: ImageResource? = null,
    textColor: Color = Color.Black
) {
    logEvent(BUTTON_CLICK_EVENT, mapOf(SCREEN_NAME to screenName, BUTTON_NAME to text))
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
        DNATextWithIcon(
            text = text,
            style = DnaTextStyle.Medium16,
            modifier = Modifier.padding(vertical = 4.dp),
            secondIcon = icon,
            iconSize = 24.dp,
            textColor = textColor
        )
    }
}

@Composable
fun DNAYellowButton(
    content: @Composable () -> Unit,
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
        shape = RoundedCornerShape(12.dp),
    ) {
        content()
    }
}

@Composable
fun DNAOutlinedGreenButton(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, greenButtonNotFilled),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        content()
    }
}

@Composable
fun DNAGreenBackButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.noRippleClickable {
            onClick()
        },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(MR.images.ic_green_arrow_back),
            contentDescription = null,
            tint = greenButtonNotFilled
        )
        Text(
            text = text,
            style = DnaTextStyle.GreenMedium16,
            modifier = Modifier.padding(vertical = 4.dp).padding(start = 4.dp)
        )
    }
}

@Composable
fun BasicCountdownTimer(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    var timeLeft by remember { mutableStateOf(initialTime) }
    var flag by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = flag) {
        while (timeLeft > 0 && flag) {
            delay(delayInMillis)
            timeLeft--
        }
        if (timeLeft == 0) {
            flag = false
        }
    }

    DNAText(
        text = if (flag) {
            stringResource(MR.strings.resend_with_time, timeLeft)
        } else {
            stringResource(MR.strings.resend)
        },
        modifier = modifier.noRippleClickable {
            flag = true
            timeLeft = initialTime
            onClick()
        }.fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = if (flag) {
            DnaTextStyle.WithAlpha16
        } else {
            DnaTextStyle.GreenMedium16
        }
    )
}