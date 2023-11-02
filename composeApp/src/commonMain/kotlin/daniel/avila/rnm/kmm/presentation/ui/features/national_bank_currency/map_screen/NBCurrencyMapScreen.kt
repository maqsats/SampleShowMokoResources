package daniel.avila.rnm.kmm.presentation.ui.features.national_bank_currency.map_screen

import androidx.compose.foundation.background
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
import daniel.avila.rnm.kmm.domain.model.time_period_tab.TimePeriod
import daniel.avila.rnm.kmm.presentation.ui.features.toolbar.Toolbar
import daniel.avila.rnm.kmm.utils.maps.CameraPosition
import daniel.avila.rnm.kmm.utils.maps.GoogleMaps
import daniel.avila.rnm.kmm.utils.maps.LatLong
import daniel.avila.rnm.kmm.utils.maps.MapMarker
import daniel.avila.rnm.kmm.utils.navigation.LocalNavigator
import daniel.avila.rnm.kmm.utils.navigation.currentOrThrow

class NBCurrencyMapScreen(
    private val nbCurrency: NationalBankCurrency,
    private val timePeriod: TimePeriod
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.background(Color.White).fillMaxSize()
        ) {
            Toolbar(
                title = nbCurrency.currencyName + " на карте",
                onBackClick = {
                    navigator.pop()
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            NBCurrencyMapHeader(nationalBankCurrency = nbCurrency, timePeriod = timePeriod)

            Spacer(modifier = Modifier.height(10.dp))

            GoogleMaps(
                modifier = Modifier
                    .fillMaxWidth().weight(1f),
                markers = listOf(
                    MapMarker(
                        key = "marker",
                        position = LatLong(
                            43.238949,
                            76.889709
                        ),
                        title = nbCurrency.rate.toString()
                    )
                ),
                cameraPosition = CameraPosition(
                    target = LatLong(
                        43.238949,
                        76.889709
                    ),
                    zoom = 14f
                ),
            )
        }
    }
}