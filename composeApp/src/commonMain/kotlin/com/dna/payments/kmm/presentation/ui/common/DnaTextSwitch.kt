package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.zIndex
import com.dna.payments.kmm.presentation.theme.Dimens
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.darkGreen
import com.dna.payments.kmm.presentation.theme.greyColorTextSwitch
import com.dna.payments.kmm.presentation.theme.white

@Composable
fun DnaTextSwitch(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    items: List<String>,
    onSelectionChange: (Int) -> Unit
) {
    require(items.isNotEmpty())
    Box(
        modifier = modifier.padding(Paddings.small)
            .height(Dimens.switchHeight).width(Dimens.switchWidth)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(Paddings.small))
                .border(
                    width = Dimens.minimum, greyColorTextSwitch, RoundedCornerShape(Paddings.small)
                ).zIndex(1f)
        )

        BoxWithConstraints(
            modifier = Modifier.zIndex(2f)
        ) {
            val maxWidth = this.maxWidth
            val tabWidth = maxWidth / items.size

            val indicatorOffset by animateDpAsState(
                targetValue = tabWidth * selectedIndex,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
                label = ""
            )

            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .shadow(Paddings.small, RoundedCornerShape(Paddings.small))
                    .width(tabWidth)
                    .background(white)
                    .fillMaxHeight()
            )

            Row(modifier = Modifier
                .wrapContentWidth()
                .drawWithContent {

                    drawWithLayer {
                        drawContent()

                        drawRoundRect(
                            topLeft = Offset(x = indicatorOffset.toPx(), 0f),
                            size = Size(size.width / 2, size.height),
                            color = darkGreen,
                            style = Stroke(width = Dimens.minimum.toPx()),
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
                        DNAText(
                            text = text,
                            style = if (selectedIndex == index) DnaTextStyle.Medium12 else DnaTextStyle.Medium12Grey4,
                        )
                    }
                }
            }
        }
    }
}

fun ContentDrawScope.drawWithLayer(block: ContentDrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}