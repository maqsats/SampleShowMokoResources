package com.dna.payments.kmm.presentation.ui.features.currency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.presentation.state.ComponentRectangleLineShort
import com.dna.payments.kmm.presentation.state.ManagementResourceUiState
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.Paddings
import com.dna.payments.kmm.presentation.theme.white
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.features.overview_report.OverviewReportContract
import com.dna.payments.kmm.utils.extension.noRippleClickable
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun CurrencyWidget(
    state: OverviewReportContract.State
) {
    ManagementResourceUiState(
        resourceUiState = state.currencies,
        successView = { currencies ->
            if (currencies.isEmpty())
                return@ManagementResourceUiState
            DNAText(
                modifier = Modifier.wrapContentWidth(),
                text = state.selectedCurrency.name,
                style = DnaTextStyle.Medium14
            )
        },
        loadingView = {
            ComponentRectangleLineShort(
                modifier = Modifier
                    .width(30.dp)
            )
        },
        onCheckAgain = {},
        onTryAgain = {},
    )
}


@Composable
fun CurrencyBottomSheet(
    state: OverviewReportContract.State,
    onCurrencyChange: (Currency) -> Unit
) {
    ManagementResourceUiState(
        resourceUiState = state.currencies,
        successView = { currencies ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = white)
                    .padding(Paddings.medium),
                verticalArrangement = Arrangement.Top
            ) {
                DNAText(
                    text = stringResource(MR.strings.select_currency),
                    style = DnaTextStyle.SemiBold20
                )

                Spacer(modifier = Modifier.height(Paddings.medium))

                currencies.forEach { currency ->
                    CurrencyItem(
                        currency = currency,
                        isSelected = state.selectedCurrency == currency,
                        onCurrencyClick = {
                            onCurrencyChange(currency)
                        })
                }

                Spacer(modifier = Modifier.height(Paddings.medium))
            }
        },
        loadingView = {
            LazyColumn {
                item {
                    DNAText(text = stringResource(MR.strings.all_statuses))
                }
                items(10) {
                    ComponentRectangleLineShort(
                        modifier = Modifier
                            .width(30.dp)
                    )
                }
            }
        },
        onCheckAgain = {},
        onTryAgain = {},
    )
}

@Composable
fun CurrencyItem(currency: Currency, isSelected: Boolean, onCurrencyClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = Paddings.standard
            )
            .noRippleClickable {
                if (!isSelected) {
                    onCurrencyClick()
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DNAText(
            modifier = Modifier.weight(1f),
            text = currency.name,
            style = if (isSelected) DnaTextStyle.SemiBold16 else DnaTextStyle.Medium16Grey5,
        )
        if (isSelected)
            Icon(
                modifier = Modifier.padding(start = Paddings.medium),
                painter = painterResource(
                    MR.images.ic_success
                ),
                tint = Color.Unspecified,
                contentDescription = null,
            )
    }
}
