package daniel.avila.rnm.kmm.presentation.ui.features.main.exchange_list_main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate

@Composable
fun ExchangeRateList(
    exchangeRateList: List<ExchangeRate>,
    onExchangeRateClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        items(exchangeRateList) { exchangeRate ->
            ExchangeRateItem(
                item = exchangeRate,
                onClick = { onExchangeRateClick(exchangeRate.id) }
            )
        }
    }
}