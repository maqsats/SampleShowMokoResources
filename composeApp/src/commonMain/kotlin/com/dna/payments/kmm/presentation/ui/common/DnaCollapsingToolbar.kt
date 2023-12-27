package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.presentation.theme.Dimens
import com.dna.payments.kmm.presentation.theme.drawerBackgroundColor
import com.dna.payments.kmm.utils.dimension.DimensionSubComposeLayout
import com.dna.payments.kmm.utils.extension.bottomShadow
import com.dna.payments.kmm.utils.toolbar.CollapsingToolbarScaffold
import com.dna.payments.kmm.utils.toolbar.ScrollStrategy
import com.dna.payments.kmm.utils.toolbar.rememberCollapsingToolbarScaffoldState

typealias MerchantName = String

val LocalSelectedMerchant = compositionLocalOf {
    ""
}

@Composable
fun DnaCollapsingToolbar(
    drawerState: DrawerState,
    isFilterEnabled: Boolean,
    isToolbarCollapsed: (Boolean) -> Unit,
    headerContent: @Composable () -> Unit,
    filterContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    if (isFilterEnabled) {
        DnaCollapsingToolbarWithFilter(
            isToolbarCollapsed = isToolbarCollapsed,
            drawerState = drawerState,
            headerContent = headerContent,
            filterContent = filterContent,
            content = content
        )
    } else {
        DnaCollapsingToolbarWithoutFilter(
            drawerState = drawerState,
            headerContent = headerContent,
            content = content
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DnaCollapsingToolbarWithoutFilter(
    drawerState: DrawerState,
    headerContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    LazyColumn(
        Modifier
            .fillMaxSize().background(drawerBackgroundColor)
    ) {
        stickyHeader {
            DnaToolbar(
                drawerState = drawerState,
                shadowDp = 10.dp
            )
        }
        item {
            headerContent()
        }
        item {
            content()
        }
    }
}

@Composable
fun DnaCollapsingToolbarWithFilter(
    drawerState: DrawerState,
    isToolbarCollapsed: (Boolean) -> Unit,
    headerContent: @Composable () -> Unit,
    filterContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    val state = rememberCollapsingToolbarScaffoldState()

    var columnHeightDp by remember {
        mutableStateOf(0.dp)
    }

    val density = LocalDensity.current

    CollapsingToolbarScaffold(
        modifier = Modifier
            .fillMaxSize().background(drawerBackgroundColor),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (columnHeightDp > 0.dp) columnHeightDp else Dimens.collapsingToolbarHeight)
                    .pin()
            ) {
                AnimatedVisibility(
                    visible = state.toolbarState.progress > 0.8f,
                    enter = fadeIn(animationSpec = tween(1000)),
                    exit = fadeOut(animationSpec = tween(1000))
                ) {
                    Column {
                        DnaToolbar(
                            drawerState = drawerState,
                            shadowDp = (state.toolbarState.progress * 10).toInt().dp
                        )
                        DimensionSubComposeLayout(
                            mainContent = { headerContent() },
                            dependentContent = { size: Size ->
                                headerContent()
                                val dpSize = density.run { size.toDpSize() }
                                columnHeightDp = dpSize.height + Dimens.toolbarAndFilterHeight
                            },
                            placeMainContent = false
                        )
                    }
                }
            }
            Box(
                Modifier.fillMaxWidth()
                    .height(Dimens.toolbarHeight)
                    .background(
                        Color.White.copy(alpha = 1f - state.toolbarState.progress)
                    )
                    .road(Alignment.TopStart, Alignment.BottomStart),
                contentAlignment = Alignment.CenterStart
            ) {
                filterContent()
            }
        }
    ) {
        content()
        val reverseShadow = (10 - state.toolbarState.progress * 10).toInt().dp
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .bottomShadow(if (reverseShadow > 7.dp) reverseShadow else 0.dp)
                .height(1.dp)
        )
        isToolbarCollapsed(state.toolbarState.progress != 1.0f)
    }
}
