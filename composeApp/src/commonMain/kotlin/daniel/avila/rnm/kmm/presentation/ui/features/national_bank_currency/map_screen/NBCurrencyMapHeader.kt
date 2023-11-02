package daniel.avila.rnm.kmm.presentation.ui.features.national_bank_currency.map_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency
import daniel.avila.rnm.kmm.domain.model.time_period_tab.TimePeriod
import daniel.avila.rnm.kmm.utils.extension.convertTimePeriodDateStyle
import daniel.avila.rnm.kmm.utils.extension.formatMoney


@Composable
fun NBCurrencyMapHeader(nationalBankCurrency: NationalBankCurrency, timePeriod: TimePeriod) {
    Column(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = convertTimePeriodDateStyle(timePeriod),
            style = MaterialTheme.typography.h4,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = "Средний курс " + nationalBankCurrency.rate.formatMoney(),
            style = MaterialTheme.typography.caption,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}