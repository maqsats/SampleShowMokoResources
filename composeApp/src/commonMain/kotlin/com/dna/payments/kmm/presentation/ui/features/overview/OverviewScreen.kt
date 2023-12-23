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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DnaFilter
import com.dna.payments.kmm.presentation.ui.common.DnaTabRow
import com.dna.payments.kmm.presentation.ui.common.DnaTextSwitch
import com.dna.payments.kmm.presentation.ui.features.currency.CurrencyBottomSheet
import com.dna.payments.kmm.presentation.ui.features.currency.CurrencyWidget
import com.dna.payments.kmm.presentation.ui.features.date_range.DateRangeBottomSheet
import com.dna.payments.kmm.presentation.ui.features.date_range.DateRangeWidget
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen
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
        var selectedTabIndex by remember {
            mutableStateOf(0)
        }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState,
            userScrollEnabled = false,
            pageContent = { pageIndex ->
                when (pageIndex) {
                    POS_PAYMENTS -> {
                        DnaTextSwitch(
                            selectedIndex = selectedTabIndex,
                            items = listOf("Amount", "Count"),
                            onSelectionChange = { index ->
                                selectedTabIndex = index
                            }
                        )
                    }

                    ONLINE_PAYMENTS -> {

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

    companion object {
        private const val POS_PAYMENTS = 0
        private const val ONLINE_PAYMENTS = 1
    }
}