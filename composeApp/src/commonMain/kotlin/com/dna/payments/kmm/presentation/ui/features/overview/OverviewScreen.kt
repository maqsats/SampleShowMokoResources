package com.dna.payments.kmm.presentation.ui.features.overview

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.domain.model.overview.OverviewType
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DnaFilter
import com.dna.payments.kmm.presentation.ui.common.DnaTabRow
import com.dna.payments.kmm.presentation.ui.common.LocalSelectedMerchant
import com.dna.payments.kmm.presentation.ui.features.currency.CurrencyBottomSheet
import com.dna.payments.kmm.presentation.ui.features.currency.CurrencyWidget
import com.dna.payments.kmm.presentation.ui.features.date_range.DateRangeBottomSheet
import com.dna.payments.kmm.presentation.ui.features.date_range.DateRangeWidget
import com.dna.payments.kmm.presentation.ui.features.overview.widgets.OverviewWidget
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalFoundationApi::class)
class OverviewScreen : DrawerScreen {

    override val isFilterEnabled = true

    @Composable
    override fun Content() {

    }

    @Composable
    override fun DrawerContent(isToolbarCollapsed: Boolean) {
        val overviewScreen = getScreenModel<OverviewViewModel>()
        val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })
        val state by overviewScreen.uiState.collectAsState()

        LaunchedEffect(key1 = Unit) {
            overviewScreen.effect.collectLatest { effect ->
                when (effect) {
                    is OverviewContract.Effect.OnPageChanged -> {
                        pagerState.animateScrollToPage(effect.position)
                    }
                }
            }
        }

        LaunchedEffect(
            LocalSelectedMerchant.current
        ) {
            overviewScreen.setEvent(OverviewContract.Event.OnMerchantChanged)
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                overviewScreen.setEvent(OverviewContract.Event.OnPageChanged(page))
            }
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            userScrollEnabled = false,
            pageContent = { pageIndex ->
                when (pageIndex) {
                    OverviewType.POS_PAYMENTS.pageId -> {
                        OverviewWidget(
                            state = state,
                            overviewType = OverviewType.POS_PAYMENTS
                        )
                    }
                    OverviewType.ONLINE_PAYMENTS.pageId -> {
                        OverviewWidget(
                            state = state,
                            overviewType = OverviewType.ONLINE_PAYMENTS
                        )
                    }
                }
            }
        )
    }

    @Composable
    override fun DrawerHeader() {
        val overviewScreen = getScreenModel<OverviewViewModel>()
        val state by overviewScreen.uiState.collectAsState()

        DnaTabRow(
            tabList = OverviewType.values().map { it.displayName },
            selectedPagePosition = state.selectedPage,
            onTabClick = {
                overviewScreen.setEvent(OverviewContract.Event.OnPageChanged(it))
            }
        )
    }

    @Composable
    override fun DrawerFilter() {
        val overviewScreen = getScreenModel<OverviewViewModel>()
        val state by overviewScreen.uiState.collectAsState()

        val openDatePickerFilter = rememberSaveable { mutableStateOf(false) }
        val openCurrencyFilter = rememberSaveable { mutableStateOf(false) }

        LazyRow(modifier = Modifier.padding(start = Paddings.small)) {
            item {
                DnaFilter(
                    openBottomSheet = openCurrencyFilter,
                    dropDownContent = {
                        CurrencyWidget(state)
                    },
                    bottomSheetContent = {
                        CurrencyBottomSheet(
                            state = state,
                            onCurrencyChange = {
                                openCurrencyFilter.value = false
                                overviewScreen.setEvent(
                                    OverviewContract.Event.OnCurrencyChange(
                                        it
                                    )
                                )
                            }
                        )
                    }
                )
            }
            item {
                DnaFilter(
                    openBottomSheet = openDatePickerFilter,
                    dropDownContent = {
                        DateRangeWidget(
                            state.dateRange.first
                        )
                    },
                    bottomSheetContent = {
                        DateRangeBottomSheet(
                            dateSelection = state.dateRange.second,
                            onDatePeriodClick = {
                                openDatePickerFilter.value = false
                                overviewScreen.setEvent(
                                    OverviewContract.Event.OnDateSelection(
                                        it
                                    )
                                )
                            }
                        )
                    }
                )
            }
        }
    }
}