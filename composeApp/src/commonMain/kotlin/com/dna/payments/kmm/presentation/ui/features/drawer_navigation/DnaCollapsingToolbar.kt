package com.dna.payments.kmm.presentation.ui.features.drawer_navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.presentation.theme.Dimens
import com.dna.payments.kmm.presentation.theme.drawerBackgroundColor
import com.dna.payments.kmm.utils.extension.bottomShadow
import com.dna.payments.kmm.utils.toolbar.CollapsingToolbarScaffold
import com.dna.payments.kmm.utils.toolbar.ScrollStrategy
import com.dna.payments.kmm.utils.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
fun DnaCollapsingToolbar(
    drawerState: DrawerState,
    isToolbarShadowEnabled: Boolean = true,
    headerContent: @Composable () -> Unit,
    filterContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val state = rememberCollapsingToolbarScaffoldState()

    CollapsingToolbarScaffold(
        modifier = Modifier
            .fillMaxSize().background(drawerBackgroundColor),
        state = state,
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        toolbar = {
            Box(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .height(Dimens.collapsingToolbarHeight)
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

                        if (isToolbarShadowEnabled) Spacer(modifier = Modifier.height(10.dp))
                        headerContent()
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
    }
}