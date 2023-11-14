package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.dna.payments.kmm.presentation.theme.greenButtonNotFilled

@Composable
fun MetaEntity(
    modifier: Modifier = Modifier,
    metaContent: @Composable BoxScope.() -> Unit,
    content: @Composable BoxScope.() -> Unit = {},
) {

    Box(
        modifier
            .width(IntrinsicSize.Min)
            .height(IntrinsicSize.Min),
        contentAlignment = Alignment.Center
    ) {
        Box(
            content = metaContent
        )
        content()
    }
}

@Composable
fun ProgressLoader(loadingPopup: MutableState<Boolean>) {
    if (!loadingPopup.value) return
    Popup(
        alignment = Alignment.Center,
        properties = PopupProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false,
            focusable = true
        ),
        onDismissRequest = {

        },
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .shadow(5.dp, shape = RoundedCornerShape(30.dp))
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {

            val x = 8

            MetaEntity(
                metaContent = {
                    val animation = rememberInfiniteTransition()
                    val rotation by animation.animateFloat(
                        initialValue = 0f,
                        targetValue = -360f,
                        animationSpec = infiniteRepeatable(
                            tween(3000, easing = LinearEasing),
                            RepeatMode.Restart,
                        )
                    )

                    Box(
                        modifier = Modifier.size(55.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Box(modifier = Modifier
                            .rotate(rotation)
                            .drawBehind {
                                drawArc(
                                    color = greenButtonNotFilled,
                                    startAngle = 0f,
                                    sweepAngle = 215f,
                                    useCenter = false,

                                    style = Stroke(
                                        width = size.width * .23f,
                                        cap = StrokeCap.Round
                                    )
                                )
                            }
                            .size(45.dp)
                        )
                        for (i in 0..x) {
                            Circle(i * (360f / x))
                        }
                    }
                }) {
            }
        }
    }
}

@Composable
fun Circle(offset: Float) {
    val animation = rememberInfiniteTransition()
    val rotation by animation.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(3000, easing = LinearEasing),
            RepeatMode.Restart,
        )
    )

    Box(
        modifier = Modifier
            .rotate(offset)
            .rotate(rotation)
            .width(55.dp)
    ) {
        Box(
            modifier = Modifier
                .background(greenButtonNotFilled, CircleShape)
                .size(10.dp)
        )
    }
}