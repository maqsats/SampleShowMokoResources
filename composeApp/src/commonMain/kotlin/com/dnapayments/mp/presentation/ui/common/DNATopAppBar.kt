package com.dnapayments.mp.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.black
import com.dnapayments.mp.presentation.ui.common.DNATopAppBarConstants.ACTION_LAYOUT_ID
import com.dnapayments.mp.presentation.ui.common.DNATopAppBarConstants.NAVIGATION_LAYOUT_ID
import com.dnapayments.mp.presentation.ui.common.DNATopAppBarConstants.SB_TOP_APP_BAR
import com.dnapayments.mp.presentation.ui.common.DNATopAppBarConstants.TITLE_LAYOUT_ID
import com.dnapayments.mp.utils.extension.noRippleClickable

@Composable
fun DNATopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    textStyle: TextStyle = DnaTextStyle.Normal16,
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Center,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    navigationIcon: Painter? = null,
    navigationText: String? = null,
    navigationIconContentDescription: String? = null,
    actionIcon: Painter? = null,
    actionIconContentDescription: String? = null,
    colors: DNATopAppBarColors = DNATopAppBarDefaults.topAppBarColors(),
    onNavigationClick: () -> Unit = {},
    onActionClick: () -> Unit = {},
) {
    Layout(
        modifier = modifier
            .background(colors.containerColor)
            .testTag(SB_TOP_APP_BAR),
        content = {
            Box(
                Modifier
                    .layoutId(NAVIGATION_LAYOUT_ID)
                    .padding(start = Paddings.extraSmall)
            ) {
                if (navigationIcon != null)
                    Row(
                        modifier = Modifier.noRippleClickable { onNavigationClick() },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier,
                            painter = navigationIcon,
                            contentDescription = navigationIconContentDescription,
                            tint = colors.navigationIconContentColor
                        )
                        if (navigationText != null)
                            DNAText(
                                text = navigationText,
                                style = DnaTextStyle.GreenMedium16,
                                modifier = Modifier.padding(vertical = 4.dp).padding(start = 4.dp)
                            )
                    }

            }
            Box(
                Modifier
                    .layoutId(TITLE_LAYOUT_ID)
                    .padding(
                        horizontal = Paddings.medium,
                        vertical = 11.dp
                    )
            ) {
                DNAText(
                    text = title,
                    style = textStyle,
                    textAlign = textAlign,
                    maxLines = maxLines
                )
            }
            Box(
                Modifier
                    .layoutId(ACTION_LAYOUT_ID)
                    .padding(end = Paddings.large)
            ) {
                if (actionIcon != null)
                    Icon(
                        modifier = Modifier.noRippleClickable { onActionClick() }.width(24.dp)
                            .height(24.dp),
                        painter = actionIcon,
                        contentDescription = actionIconContentDescription,
                        tint = colors.actionIconContentColor
                    )
            }
        }
    ) { measurables, constraints ->
        val navigationIconPlaceable =
            measurables.first { it.layoutId == NAVIGATION_LAYOUT_ID }
                .measure(constraints.copy(minWidth = 0))
        val actionIconsPlaceable =
            measurables.first { it.layoutId == ACTION_LAYOUT_ID }
                .measure(constraints.copy(minWidth = 0))

        val maxTitleWidth = if (constraints.maxWidth == Constraints.Infinity) {
            constraints.maxWidth
        } else {
            (constraints.maxWidth - navigationIconPlaceable.width - actionIconsPlaceable.width)
                .coerceAtLeast(0)
        }
        val titlePlaceable =
            measurables.first { it.layoutId == TITLE_LAYOUT_ID }
                .measure(constraints.copy(minWidth = 0, maxWidth = maxTitleWidth))

        val layoutHeight = maxOf(
            titlePlaceable.height,
            navigationIconPlaceable.height,
            actionIconsPlaceable.height
        )

        layout(constraints.maxWidth, layoutHeight) {

            navigationIconPlaceable.placeRelative(
                x = 0,
                y = (layoutHeight - navigationIconPlaceable.height) / 2
            )

            titlePlaceable.placeRelative(
                x = (constraints.maxWidth - titlePlaceable.width) / 2,
                y = (layoutHeight - titlePlaceable.height) / 2
            )

            actionIconsPlaceable.placeRelative(
                x = constraints.maxWidth - actionIconsPlaceable.width,
                y = (layoutHeight - actionIconsPlaceable.height) / 2
            )
        }
    }
}

private object DNATopAppBarConstants {
    const val NAVIGATION_LAYOUT_ID = "navigationIcon"
    const val TITLE_LAYOUT_ID = "title"
    const val SB_TOP_APP_BAR = "smartBankTopAppBar"
    const val ACTION_LAYOUT_ID = "actionIcons"
}

object DNATopAppBarDefaults {

    /**
     * @param containerColor the container color
     * @param navigationIconContentColor the content color used for the navigation icon
     * @param titleContentColor the content color used for the title
     * @param actionIconContentColor the content color used for actions
     * @return the resulting [TopAppBarColors] used for the top app bar
     */
    @Composable
    fun topAppBarColors(
        containerColor: Color = Color.Unspecified,
        navigationIconContentColor: Color = Color.Unspecified,
        titleContentColor: Color = black,
        actionIconContentColor: Color = Color.Unspecified,
    ): DNATopAppBarColors =
        DNATopAppBarColors(
            containerColor,
            navigationIconContentColor,
            titleContentColor,
            actionIconContentColor
        )
}

@Stable
class DNATopAppBarColors internal constructor(
    internal val containerColor: Color,
    internal val navigationIconContentColor: Color,
    internal val titleContentColor: Color,
    internal val actionIconContentColor: Color,
)
