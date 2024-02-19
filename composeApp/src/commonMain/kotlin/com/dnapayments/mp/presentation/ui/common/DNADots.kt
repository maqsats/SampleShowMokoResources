package com.dnapayments.mp.presentation.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.dnapayments.mp.MR
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.utils.extension.noRippleClickable
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun DNADots(
    modifier: Modifier = Modifier,
    isContinued: Boolean = true,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(start = Paddings.medium, end = Paddings.medium)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(MR.images.ic_ellipse),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = modifier.padding(top = Paddings.extraSmall)
            )
            if (isContinued) {
                Icon(
                    painter = painterResource(MR.images.ic_vector),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = modifier.height(52.dp)
                        .padding(top = Paddings.extraSmall)
                )
            }
        }
        Column(modifier = Modifier.padding(start = Paddings.extraLarge)) {
            content()
        }
    }
}

@Composable
fun MessageDotsContent(
    title: StringResource,
    value: String,
    modifier: Modifier = Modifier,
) {
    DNAText(
        stringResource(title),
        style = DnaTextStyle.WithAlpha12
    )
    DNATextWithIcon(
        value.ifEmpty { stringResource(MR.strings.not_applicable) },
        style = DnaTextStyle.Normal16,
        icon = MR.images.ic_message
    )
}

@Composable
fun DefaultDotsContent(
    title: StringResource,
    value: String,
    modifier: Modifier = Modifier,
) {
    DNAText(
        stringResource(title),
        style = DnaTextStyle.WithAlpha12
    )
    DNAText(
        value.ifEmpty { stringResource(MR.strings.not_applicable) },
        style = DnaTextStyle.Normal16
    )
}

@Composable
fun PainterDotsContent(
    title: StringResource,
    value: String,
    icon: ImageResource?,
    modifier: Modifier = Modifier,
) {
    DNAText(
        stringResource(title),
        style = DnaTextStyle.WithAlpha12
    )
    Row(verticalAlignment = Alignment.CenterVertically) {
        icon?.let {
            Icon(
                painter = painterResource(it),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.height(24.dp).width(24.dp)
            )
        }
        DNAText(
            modifier = modifier.padding(start = Paddings.small),
            style = DnaTextStyle.Medium14,
            text = value.ifEmpty { stringResource(MR.strings.not_applicable) }
        )
    }
}

@Composable
fun PainterWithBackgroundDotsContent(
    title: StringResource,
    value: String,
    icon: ImageResource?,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    DNAText(
        stringResource(MR.strings.transaction_type),
        style = DnaTextStyle.WithAlpha12
    )
    if (icon != null) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = Paddings.extraSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        backgroundColor,
                        CircleShape
                    )
                    .size(24.dp)
            ) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(Paddings.extraSmall)
                        .height(24.dp)
                        .width(24.dp)
                )
            }
            DNAText(
                value.ifEmpty { stringResource(MR.strings.not_applicable) },
                style = DnaTextStyle.Normal16,
                modifier = Modifier.padding(start = Paddings.small)
            )
        }
    }
}

@Composable
fun ClipboardDotsContent(
    title: StringResource,
    value: String,
    clipboardManager: ClipboardManager,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .noRippleClickable {
                clipboardManager.setText(AnnotatedString(value))
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(modifier = Modifier.wrapContentHeight().weight(1f)) {
            DNAText(
                stringResource(title),
                style = DnaTextStyle.WithAlpha12
            )
            DNAText(
                value.ifEmpty { stringResource(MR.strings.not_applicable) },
                style = DnaTextStyle.Normal16,
                maxLines = 1,
            )
        }
        Icon(
            painter = painterResource(MR.images.ic_copy),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.padding(start = Paddings.small)
                .height(24.dp).width(24.dp)
        )
    }
}

@Composable
fun LinkDotsContent(
    title: StringResource,
    value: String,
    clipboardManager: ClipboardManager,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .noRippleClickable {
                clipboardManager.setText(AnnotatedString(value))
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(modifier = Modifier.wrapContentHeight()) {
            DNAText(
                stringResource(title),
                style = DnaTextStyle.WithAlpha12
            )
            DNAText(
                value.ifEmpty { stringResource(MR.strings.not_applicable) },
                style = DnaTextStyle.GreenMedium14,
                maxLines = 2,
            )
        }
    }
}