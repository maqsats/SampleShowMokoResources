package com.dna.payments.kmm.presentation.ui.features.overview_report

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.domain.model.date_picker.Menu
import com.dna.payments.kmm.domain.model.overview_report.OverviewReportType
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DnaFilter
import com.dna.payments.kmm.presentation.ui.common.DnaTabRow
import com.dna.payments.kmm.presentation.ui.common.LocalSelectedMerchant
import com.dna.payments.kmm.presentation.ui.features.currency.CurrencyBottomSheet
import com.dna.payments.kmm.presentation.ui.features.currency.CurrencyWidget
import com.dna.payments.kmm.presentation.ui.features.date_range.DateRangeBottomSheet
import com.dna.payments.kmm.presentation.ui.features.date_range.DateRangeWidget
import com.dna.payments.kmm.utils.navigation.drawer_navigation.DrawerScreen
import org.koin.core.parameter.parametersOf

class OverviewReportScreen(private val menu: Menu) : DrawerScreen {

    override val key: ScreenKey = menu.name

    override val isFilterEnabled = true

    @Composable
    override fun Content() {
    }

    @Composable
    override fun DrawerContent(isToolbarCollapsed: Boolean) {
        val overviewReportViewModel = getScreenModel<OverviewReportViewModel> {
            parametersOf(
                menu
            )
        }

        val state by overviewReportViewModel.uiState.collectAsState()

        LaunchedEffect(
            LocalSelectedMerchant.current
        ) {
            overviewReportViewModel.setEvent(OverviewReportContract.Event.OnMerchantChanged)
        }

        OverviewWidget(
            state = state,
            overviewReportType = when (state.selectedPage) {
                OverviewReportType.POS_PAYMENTS.pageId -> OverviewReportType.POS_PAYMENTS
                else -> OverviewReportType.ONLINE_PAYMENTS
            },
            isToolbarCollapsed = isToolbarCollapsed,
            onRefresh = {
                overviewReportViewModel.setEvent(OverviewReportContract.Event.OnRefresh)
            }
        )
    }

    @Composable
    override fun DrawerHeader() {
        val overviewReportViewModel = getScreenModel<OverviewReportViewModel> {
            parametersOf(
                menu
            )
        }

        val state by overviewReportViewModel.uiState.collectAsState()

        val changePage = remember {
            { position: Int ->
                overviewReportViewModel.setEvent(OverviewReportContract.Event.OnPageChanged(position))
            }
        }

        DnaTabRow(
            tabList = OverviewReportType.entries.map { it.displayName },
            selectedPagePosition = state.selectedPage,
            onTabClick = changePage
        )
    }

    @Composable
    override fun DrawerFilter() {
        val overviewReportViewModel = getScreenModel<OverviewReportViewModel> {
            parametersOf(
                menu
            )
        }
        val state by overviewReportViewModel.uiState.collectAsState()

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
                                overviewReportViewModel.setEvent(
                                    OverviewReportContract.Event.OnCurrencyChange(
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
                                overviewReportViewModel.setEvent(
                                    OverviewReportContract.Event.OnDateSelection(
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