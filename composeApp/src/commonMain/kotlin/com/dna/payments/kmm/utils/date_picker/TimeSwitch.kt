package com.dna.payments.kmm.utils.date_picker

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dna.payments.kmm.domain.model.date_picker.DatePickerAmPm
import com.dna.payments.kmm.presentation.theme.Dimens
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.timeSwitchBg
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.drawWithLayer
import com.dna.payments.kmm.utils.extension.noRippleClickable
import dev.icerock.moko.resources.compose.stringResource
import kotlin.enums.EnumEntries

@Composable
fun DnaTimeSwitch(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    items: EnumEntries<DatePickerAmPm>,
    onSelectionChange: (Int) -> Unit
) {
    require(items.isNotEmpty())

    BoxWithConstraints(
        modifier = Modifier
            .height(Dimens.switchTimeHeight)
            .width(Dimens.switchTimeWidth)
            .clip(RoundedCornerShape(Paddings.small))
            .background(timeSwitchBg)
            .padding(Paddings.xxSmall)
            .then(modifier)
    ) {
        val maxWidth = this.maxWidth
        val tabWidth = maxWidth / items.size

        val indicatorOffset by animateDpAsState(
            targetValue = tabWidth * selectedIndex,
            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
            label = ""
        )

        // This is for shadow layer matching white background
        Box(
            modifier = Modifier
                .offset(x = indicatorOffset)
                .shadow(Paddings.extraSmall, RoundedCornerShape(Paddings.small))
                .width(tabWidth)
                .fillMaxHeight()
        )

        Row(modifier = Modifier
            .fillMaxWidth()

            .drawWithContent {

                // This is for setting black tex while drawing on white background
                val padding = Paddings.small.toPx()
                drawRoundRect(
                    topLeft = Offset(x = indicatorOffset.toPx() + padding, padding),
                    size = Size(size.width / 2 - padding * 2, size.height - padding * 2),
                    color = Color.Black,
                    cornerRadius = CornerRadius(
                        x = Paddings.small.toPx(),
                        y = Paddings.small.toPx()
                    ),
                )

                drawWithLayer {
                    drawContent()

                    // This is white top rounded rectangle
                    drawRoundRect(
                        topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                        size = Size(size.width / 2, size.height),
                        color = Color.White,
                        cornerRadius = CornerRadius(
                            x = Paddings.small.toPx(),
                            y = Paddings.small.toPx()
                        ),
                        blendMode = BlendMode.SrcOut
                    )
                }

            }
        ) {
            items.forEachIndexed { index, text ->
                Box(
                    modifier = Modifier
                        .width(tabWidth)
                        .fillMaxHeight()
                        .noRippleClickable {
                            onSelectionChange(index)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    DNAText(
                        text = stringResource(text.displayName),
                        style = if (selectedIndex == index) DnaTextStyle.Medium12 else DnaTextStyle.Medium12Grey4,
                    )
                }
            }
        }
    }
}

@Composable
private fun TextSwitch(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    items: List<String>,
    onSelectionChange: (Int) -> Unit
) {

    BoxWithConstraints(
        modifier
            .padding(8.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xfff3f3f2))
            .padding(8.dp)
    ) {
        if (items.isNotEmpty()) {

            val maxWidth = this.maxWidth
            val tabWidth = maxWidth / items.size

            val indicatorOffset by animateDpAsState(
                targetValue = tabWidth * selectedIndex,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                label = "indicator offset"
            )

            // This is for shadow layer matching white background
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .width(tabWidth)
                    .fillMaxHeight()
            )

            Row(modifier = Modifier
                .fillMaxWidth()

                .drawWithContent {

                    // This is for setting black tex while drawing on white background
                    val padding = 8.dp.toPx()
                    drawRoundRect(
                        topLeft = Offset(x = indicatorOffset.toPx() + padding, padding),
                        size = Size(size.width / 2 - padding * 2, size.height - padding * 2),
                        color = Color.Black,
                        cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                    )

                    drawWithLayer {
                        drawContent()

                        // This is white top rounded rectangle
                        drawRoundRect(
                            topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                            size = Size(size.width / 2, size.height),
                            color = Color.White,
                            cornerRadius = CornerRadius(x = 8.dp.toPx(), y = 8.dp.toPx()),
                            blendMode = BlendMode.SrcOut
                        )
                    }

                }
            ) {
                items.forEachIndexed { index, text ->
                    Box(
                        modifier = Modifier
                            .width(tabWidth)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null,
                                onClick = {
                                    onSelectionChange(index)
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = text,
                            fontSize = 20.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}