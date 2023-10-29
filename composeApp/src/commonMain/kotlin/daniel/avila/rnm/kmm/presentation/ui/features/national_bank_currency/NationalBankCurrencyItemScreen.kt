package daniel.avila.rnm.kmm.presentation.ui.features.national_bank_currency

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import daniel.avila.rnm.kmm.domain.model.national_bank.NationalBankCurrency
import daniel.avila.rnm.kmm.presentation.ui.features.line_chart.LineChartPreview
import daniel.avila.rnm.kmm.presentation.ui.features.national_bank_currency.time_period_tab.TimePeriodsTab
import daniel.avila.rnm.kmm.presentation.ui.features.toolbar.Toolbar
import daniel.avila.rnm.kmm.utils.navigation.LocalNavigator
import daniel.avila.rnm.kmm.utils.navigation.currentOrThrow

class NationalBankCurrencyItemScreen(private val nationalBankCurrency: NationalBankCurrency) :
    Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.background(Color.White).fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            Toolbar(
                title = nationalBankCurrency.currencyName,
                onBackClick = {
                    navigator.pop()
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            NationalBankCurrencyHeader(nationalBankCurrency = nationalBankCurrency)

            Spacer(modifier = Modifier.height(50.dp))

            TimePeriodsTab(modifier = Modifier.fillMaxWidth(), onTabSelected = {

            })

            LineChartPreview()
        }
    }
}