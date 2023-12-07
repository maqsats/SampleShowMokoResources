package com.dna.payments.kmm.presentation.ui.features.overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.ui.common.DnaTabRow
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalFoundationApi::class)
class OverviewScreen : DrawerScreen {

    override val isFilterEnabled = true

    @Composable
    override fun Content() {

        val overviewScreen = getScreenModel<OverviewViewModel>()
        val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })

        LaunchedEffect(key1 = Unit) {
            overviewScreen.effect.collectLatest { effect ->
                when (effect) {
                    is OverviewContract.Effect.OnPageChanged -> {
                        pagerState.animateScrollToPage(effect.position)
                    }
                }
            }
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                overviewScreen.setEvent(OverviewContract.Event.OnPageChanged(page))
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            userScrollEnabled = true,
            pageContent = {}
        )
    }

    @Composable
    override fun DrawerHeader() {
        val overviewScreen = getScreenModel<OverviewViewModel>()
        val state by overviewScreen.uiState.collectAsState()

        DnaTabRow(
            tabList = listOf(
                stringResource(MR.strings.pos_payments),
                stringResource(MR.strings.online_payments),
            ),
            selectedPagePosition = state.selectedPage,
            onTabClick = {
                overviewScreen.setEvent(OverviewContract.Event.OnPageChanged(it))
            }
        )
    }

    @Composable
    override fun DrawerFilter() {
        Image(
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.CenterStart,
            painter = painterResource(MR.images.header),
            contentDescription = null
        )
    }
}