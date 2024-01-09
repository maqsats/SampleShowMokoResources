package com.dna.payments.kmm.presentation.ui.features.overview.widgets

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.domain.model.overview.OverviewType
import com.dna.payments.kmm.domain.model.overview.OverviewWidgetItem
import com.dna.payments.kmm.domain.model.overview.OverviewWidgetType.*
import com.dna.payments.kmm.presentation.theme.Dimens
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.white
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.features.overview.OverviewContract
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun OverviewWidget(
    state: OverviewContract.State,
    overviewType: OverviewType
) {
    val widthSizeClass = calculateWindowSizeClass().widthSizeClass
    val isCompactScreen = widthSizeClass == WindowWidthSizeClass.Compact

    if (isCompactScreen)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(
                items = state.overviewWidgetItems.filter {
                    it.overviewType == overviewType
                },
                key = {
                    it.overviewWidgetType.name
                }
            )
            { widgetItem ->
                OverviewWidgetContainer(
                    overviewWidgetItem = widgetItem,
                    state = state,
                    overviewType = overviewType
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
            items(state.overviewWidgetItems.filter {
                it.overviewType == overviewType
            }) { widgetItem ->
                OverviewWidgetContainer(
                    overviewWidgetItem = widgetItem,
                    state = state,
                    overviewType = overviewType
                )
            }
        }
    }
}

@Composable
fun OverviewWidgetContainer(
    modifier: Modifier = Modifier,
    overviewWidgetItem: OverviewWidgetItem,
    state: OverviewContract.State,
    overviewType: OverviewType
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

        if (overviewWidgetItem.overviewWidgetType != PRODUCT_GUIDE) {
            OverviewWidgetHeader(
                title = overviewWidgetItem.title,
                hint = overviewWidgetItem.hint
            )
        }

        when (overviewWidgetItem.overviewWidgetType) {
            APPROVAL_RATE -> {
                ApprovalRateWidget(
                    state.posPaymentsSummaryList,
                    state.onlinePaymentsSummaryList,
                    state.selectedCurrency,
                    overviewType
                )
            }
            AVERAGE_METRICS -> {
                val metricResourceUiState = when (overviewType) {
                    OverviewType.POS_PAYMENTS -> state.posPaymentsMetricList
                    OverviewType.ONLINE_PAYMENTS -> state.onlinePaymentsMetricList
                }
                AverageMetricsWidget(metricResourceUiState, state.selectedCurrency)
            }
            CHARGED_TRANSACTIONS -> {
                ChargedTransactionsWidget(state.onlinePaymentsGraphSummary, state.selectedCurrency)
            }
            ALL_POS_TRANSACTIONS -> {
                PosTransactionsWidget(state.posPaymentsGraphSummary, state.selectedCurrency)
            }
            CHARGED_TRANSACTIONS_COMPARISON -> {
                PosTransactionsWidget(
                    state.posPaymentsGraphSummary,
                    state.selectedCurrency,
                    showComparison = true
                )
            }
            PRODUCT_GUIDE -> {
                ProductGuideWidget(state.productGuideList)
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