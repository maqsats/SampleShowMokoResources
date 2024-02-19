package com.dnapayments.mp.presentation.ui.features.overview_report

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.dnapayments.mp.domain.model.overview_report.OverviewReportType
import com.dnapayments.mp.domain.model.overview_report.OverviewReportWidgetItem
import com.dnapayments.mp.domain.model.overview_report.OverviewReportWidgetType.APPROVAL_RATE
import com.dnapayments.mp.domain.model.overview_report.OverviewReportWidgetType.AVERAGE_METRICS
import com.dnapayments.mp.domain.model.overview_report.OverviewReportWidgetType.CARD_SCHEMES_CHART
import com.dnapayments.mp.domain.model.overview_report.OverviewReportWidgetType.CHARGED_TRANSACTIONS_COMPARISON
import com.dnapayments.mp.domain.model.overview_report.OverviewReportWidgetType.ISSUING_BANKS_CHART
import com.dnapayments.mp.domain.model.overview_report.OverviewReportWidgetType.PAYMENT_METHODS_CHART
import com.dnapayments.mp.domain.model.overview_report.OverviewReportWidgetType.PRODUCT_GUIDE
import com.dnapayments.mp.domain.model.overview_report.OverviewReportWidgetType.TRANSACTIONS
import com.dnapayments.mp.presentation.theme.Dimens
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.Paddings
import com.dnapayments.mp.presentation.theme.white
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.presentation.ui.features.overview_report.widgets.approval_average_metrics.ApprovalAverageMetricsWidget
import com.dnapayments.mp.presentation.ui.features.overview_report.widgets.chart.ChartWidget
import com.dnapayments.mp.presentation.ui.features.overview_report.widgets.product_guide.ProductGuideWidget
import com.dnapayments.mp.presentation.ui.features.overview_report.widgets.transactions.TransactionsWidget
import com.dnapayments.mp.utils.pull_to_refresh.PullToRefresh
import com.dnapayments.mp.utils.pull_to_refresh.rememberPullToRefreshState
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun OverviewWidget(
    state: OverviewReportContract.State,
    overviewReportType: OverviewReportType,
    isToolbarCollapsed: Boolean,
    onRefresh: () -> Unit,
) {
    val widthSizeClass = calculateWindowSizeClass().widthSizeClass
    val isCompactScreen = widthSizeClass == WindowWidthSizeClass.Compact

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        delay(1000)
        if (isRefreshing)
            isRefreshing = false
    }

    PullToRefresh(
        modifier = Modifier
            .fillMaxSize(),
        state = rememberPullToRefreshState(isRefreshing = isRefreshing),
        onRefresh = onRefresh,
        enabled = !isToolbarCollapsed
    ) {
        if (isCompactScreen)
            LazyColumn {
                items(
                    items = state.overviewReportWidgetItems.filter {
                        it.overviewReportType == overviewReportType
                    },
                    key = {
                        "${it.overviewReportWidgetType.name}${it.overviewReportType}"
                    }
                )
                { widgetItem ->
                    OverviewWidgetContainer(
                        overviewReportWidgetItem = widgetItem,
                        state = state,
                        overviewReportType = overviewReportType
                    )
                }
            }
        else {
            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .fillMaxSize().padding(bottom = Dimens.toolbarHeight),
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(0.dp),
            ) {
                items(state.overviewReportWidgetItems.filter {
                    it.overviewReportType == overviewReportType
                }) { widgetItem ->
                    OverviewWidgetContainer(
                        overviewReportWidgetItem = widgetItem,
                        state = state,
                        overviewReportType = overviewReportType
                    )
                }
            }
        }
    }
}

@Composable
fun OverviewWidgetContainer(
    modifier: Modifier = Modifier,
    overviewReportWidgetItem: OverviewReportWidgetItem,
    state: OverviewReportContract.State,
    overviewReportType: OverviewReportType
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Paddings.medium, vertical = Paddings.small)
            .shadow(Paddings.extraSmall, RoundedCornerShape(Paddings.extraSmall))
            .background(
                color = white,
                shape = RoundedCornerShape(Paddings.small)
            ).then(modifier),
    ) {

        if (overviewReportWidgetItem.overviewReportWidgetType != PRODUCT_GUIDE) {
            OverviewWidgetHeader(
                title = overviewReportWidgetItem.title,
                hint = overviewReportWidgetItem.hint
            )
        }

        when (overviewReportWidgetItem.overviewReportWidgetType) {
            APPROVAL_RATE, AVERAGE_METRICS -> {
                ApprovalAverageMetricsWidget(
                    overviewReportType = overviewReportType,
                    dateSelection = state.dateRange.second,
                    selectedCurrency = state.selectedCurrency,
                    isApprovalRate = overviewReportWidgetItem.overviewReportWidgetType == APPROVAL_RATE
                ).Content()
            }
            TRANSACTIONS, CHARGED_TRANSACTIONS_COMPARISON -> {
                TransactionsWidget(
                    overviewReportType = overviewReportType,
                    dateSelection = state.dateRange.second,
                    selectedCurrency = state.selectedCurrency,
                    showComparison = overviewReportWidgetItem.overviewReportWidgetType == CHARGED_TRANSACTIONS_COMPARISON,
                    menuType = state.menuType
                ).Content()
            }
            PRODUCT_GUIDE -> {
                ProductGuideWidget.Content()
            }
            PAYMENT_METHODS_CHART, CARD_SCHEMES_CHART, ISSUING_BANKS_CHART -> {
                ChartWidget(
                    overviewReportType = overviewReportType,
                    dateSelection = state.dateRange.second,
                    selectedCurrency = state.selectedCurrency,
                    widgetType = overviewReportWidgetItem.overviewReportWidgetType
                ).Content()
            }
        }
    }
}


@Composable
fun OverviewWidgetHeader(title: StringResource, hint: StringResource? = null) {
    Column {
        DNAText(
            modifier = Modifier.padding(
                top = Paddings.medium,
                bottom = Paddings.small,
                start = Paddings.medium,
                end = Paddings.medium
            ),
            text = stringResource(title),
            style = DnaTextStyle.Medium16
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}