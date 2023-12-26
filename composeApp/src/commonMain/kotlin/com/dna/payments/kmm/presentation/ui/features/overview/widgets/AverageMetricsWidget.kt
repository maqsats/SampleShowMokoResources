package com.dna.payments.kmm.presentation.ui.features.overview.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dna.payments.kmm.domain.model.average_metrics.Metric
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.overview.OverviewType
import com.dna.payments.kmm.presentation.state.ComponentRectangleLineShort
import com.dna.payments.kmm.presentation.state.ManagementResourceUiState
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.features.overview.OverviewContract
import com.dna.payments.kmm.utils.extension.toMoneyString
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun AverageMetricsWidget(
    state: OverviewContract.State,
    overviewType: OverviewType
) {
    val metricResourceUiState = when (overviewType) {
        OverviewType.POS_PAYMENTS -> state.posPaymentsMetricList
        OverviewType.ONLINE_PAYMENTS -> state.onlinePaymentsMetricList
    }
    ManagementResourceUiState(
        modifier = Modifier.fillMaxWidth(),
        resourceUiState = metricResourceUiState,
        onCheckAgain = {},
        onTryAgain = {},
        successView = {
            AverageMetricsWidgetSuccess(it, state.selectedCurrency)
        },
        loadingView = {
            AverageMetricsWidgetLoading()
        },
        msgCheckAgain = {}
    )
}

@Composable
fun AverageMetricsWidgetSuccess(metricList: List<Metric>, currency: Currency) {
    Column(
        modifier = Modifier.padding(
            horizontal = Paddings.medium
        )
    ) {
        metricList.forEach { metric ->

            if (metric != metricList.first())
                Divider(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(Paddings.small))

            DNAText(
                text = stringResource(metric.description.stringResource),
                style = DnaTextStyle.Medium12Grey4
            )

            DNAText(
                text = metric.amount.toMoneyString(currency.name),
                style = DnaTextStyle.SemiBold16
            )

            Spacer(modifier = Modifier.height(Paddings.small))
        }
    }
}


@Composable
fun AverageMetricsWidgetLoading() {
    Column(
        modifier = Modifier.padding(
            horizontal = Paddings.medium
        )
    ) {
        repeat(3) {
            if (it != 0)
                Divider(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(Paddings.small))

            ComponentRectangleLineShort()

            Spacer(modifier = Modifier.height(Paddings.small))
        }
    }
}