package com.dnapayments.mp.presentation.ui.common

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.dnapayments.mp.presentation.theme.Dimens
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.dnaGreenLight
import com.dnapayments.mp.presentation.theme.white
import com.dnapayments.mp.utils.extension.bottomShadow
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun DnaTabRow(
    modifier: Modifier = Modifier,
    tabList: List<StringResource>,
    selectedPagePosition: Int,
    onTabClick: (Int) -> Unit
) {
    TabRow(
        selectedTabIndex = selectedPagePosition,
        modifier = modifier.fillMaxWidth().height(Dimens.tabBarHeight)
            .bottomShadow(Paddings.extraSmall),
        backgroundColor = white,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedPagePosition]),
                color = dnaGreenLight,
                height = 2.dp,
            )
        }
    ) {
        tabList.forEachIndexed { index, title ->
            DnaTab(
                selected = selectedPagePosition == index,
                onClick = {
                    onTabClick(index)
                },
                content = {
                    DNAText(
                        text = stringResource(title),
                        style = DnaTextStyle.Medium16
                    )
                }
            )
        }
    }
}

@Composable
fun DnaTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor.copy(alpha = ContentAlpha.medium),
    content: @Composable ColumnScope.() -> Unit
) {
    TabTransition(
        selectedContentColor,
        unselectedContentColor,
        selected
    ) {
        Column(
            modifier = modifier
                .selectable(
                    selected = selected,
                    onClick = onClick,
                    enabled = enabled,
                    role = Role.Tab,
                    interactionSource = interactionSource,
                    indication = null
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            content = content
        )
    }
}


@Composable
fun TabTransition(
    activeColor: Color,
    inactiveColor: Color,
    selected: Boolean,
    content: @Composable () -> Unit
) {
    val transition = updateTransition(selected)
    val color by transition.animateColor(
        transitionSpec = {
            if (false isTransitioningTo true) {
                tween(
                    durationMillis = 150,
                    delayMillis = 100,
                    easing = LinearEasing
                )
            } else {
                tween(
                    durationMillis = 100,
                    easing = LinearEasing
                )
            }
        }
    ) {
        if (it) activeColor else inactiveColor
    }
    CompositionLocalProvider(
        LocalContentColor provides color.copy(alpha = 1f),
        LocalContentAlpha provides color.alpha,
        content = content
    )
}