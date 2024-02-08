package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.state.ComponentRectangle
import com.dna.payments.kmm.presentation.state.ComponentRectangleLineLong
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.greyColorBackground
import com.dna.payments.kmm.utils.extension.noRippleClickable
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun DNAExpandBox(
    modifier: Modifier = Modifier,
    title: StringResource,
    icon: ImageResource,
    isFirst: Boolean = false,
    content: @Composable () -> Unit,
) {
    var isExpanded by remember { mutableStateOf<Boolean?>(isFirst) }
    var currentRotation by remember { mutableStateOf(0f) }
    val rotation = remember { Animatable(currentRotation) }
    Box(
        modifier.fillMaxWidth().wrapContentHeight().background(Color.White)
            .padding(top = Paddings.medium, bottom = Paddings.medium, start = Paddings.medium, end = Paddings.medium)
            .animateContentSize()
            .noRippleClickable {
                isExpanded = isExpanded != true
            }
    ) {
        LaunchedEffect(isExpanded)
        {
            if (isExpanded == null) return@LaunchedEffect
            rotation.animateTo(
                targetValue = currentRotation + 180f,
                animationSpec = tween(
                    durationMillis = 200,
                    easing = LinearOutSlowInEasing
                )
            ) {
                currentRotation = value
            }
        }
        Column {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = modifier
                            .shadow(1.dp, shape = RoundedCornerShape(4.dp))
                            .border(
                                BorderStroke(width = 1.dp, color = greyColorBackground),
                                shape = RoundedCornerShape(4.dp)
                            ).height(40.dp)
                            .background(Color.White)
                            .width(40.dp).padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                    }
                    DNAText(
                        text = stringResource(title),
                        modifier = Modifier.padding(horizontal = Paddings.medium),
                        style = DnaTextStyle.Normal16
                    )
                }
                Icon(
                    painter = painterResource(if (isFirst) MR.images.ic_arrow_expanded_up else MR.images.ic_arrow_expanded_down),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.rotate(rotation.value)
                )
            }
            Spacer(modifier = Modifier.height(Paddings.small))
            if (isExpanded == true) {
                Spacer(modifier = Modifier.height(Paddings.small))
                Column {
                    content()
                }
            }
        }
    }
}

@Composable
fun DNAExpandBoxOnLoading(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier.fillMaxWidth().wrapContentHeight().background(Color.White)
            .padding(
                top = Paddings.medium,
                bottom = Paddings.medium,
                start = Paddings.medium,
                end = Paddings.medium
            )

    ) {
        Column {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = modifier
                            .shadow(1.dp, shape = RoundedCornerShape(4.dp))
                            .border(
                                BorderStroke(width = 1.dp, color = greyColorBackground),
                                shape = RoundedCornerShape(4.dp)
                            ).height(40.dp)
                            .background(Color.White)
                            .width(40.dp).padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ComponentRectangle(modifier = modifier.padding(Paddings.extraSmall))
                    }
                    ComponentRectangleLineLong(modifier = modifier.padding(horizontal = Paddings.medium))
                }
            }
            Spacer(modifier = Modifier.height(Paddings.small))
        }
    }
}