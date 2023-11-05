package daniel.avila.rnm.kmm.presentation.ui.features.calculator.exchange_list_main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.domain.model.exchange_rate.BuyOrSell
import daniel.avila.rnm.kmm.domain.params.ExchangeRateParameters
import daniel.avila.rnm.kmm.presentation.state.ManagementResourceUiState
import daniel.avila.rnm.kmm.presentation.ui.common.LocalSelectedCity
import daniel.avila.rnm.kmm.presentation.ui.features.screen2.Screen2
import daniel.avila.rnm.kmm.utils.maps.geo.BindLocationTrackerEffect
import daniel.avila.rnm.kmm.utils.maps.geo.LocationTrackerAccuracy
import daniel.avila.rnm.kmm.utils.maps.geo.rememberLocationTrackerFactory
import daniel.avila.rnm.kmm.utils.navigation.LocalNavigator
import daniel.avila.rnm.kmm.utils.navigation.currentOrThrow
import daniel.avila.rnm.kmm.utils.permissions.PermissionsController
import daniel.avila.rnm.kmm.utils.permissions.compose.BindEffect
import daniel.avila.rnm.kmm.utils.permissions.compose.PermissionsControllerFactory
import daniel.avila.rnm.kmm.utils.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.flow.collectLatest


class ExchangeRateListMain(
    val modifier: Modifier,
    private val buyOrSell: BuyOrSell,
    val text: String,
    private val currencyPair: Pair<Currency, Currency>
) : Screen {

    @Composable
    override fun Content() {
        val exchangeRateViewModel = getScreenModel<ExchangeRateViewModel>()

        val state by exchangeRateViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
        val controller: PermissionsController =
            remember(factory) { factory.createPermissionsController() }
        BindEffect(controller)

        val locationTracker =
            rememberLocationTrackerFactory(accuracy = LocationTrackerAccuracy.Best)
                .createLocationTracker(controller)

        val selectedCity = LocalSelectedCity.current

        BindLocationTrackerEffect(locationTracker)

        LaunchedEffect(selectedCity, currencyPair) {
            if (selectedCity == null) return@LaunchedEffect

            exchangeRateViewModel.setEvent(
                ExchangeRateContract.Event.OnFetchData(
                    param = ExchangeRateParameters(
                        cityId = selectedCity.id,
                        lat = 43.238949,
                        lng = 76.889709,
                        currencyCode = currencyPair.second.code
                    ),
                    inputText = text,
                    currencies = currencyPair
                )
            )
        }

        LaunchedEffect(text) {
            if (text.isEmpty()) return@LaunchedEffect
            exchangeRateViewModel.setEvent(
                ExchangeRateContract.Event.OnInputValueChange(
                    inputText = text
                )
            )
        }

        LaunchedEffect(key1 = Unit) {
            exchangeRateViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is ExchangeRateContract.Effect.NavigateToDetailExchangeRate -> {
                        navigator.push(Screen2())
                    }
                }
            }
        }

        ManagementResourceUiState(
            modifier = modifier
                .fillMaxSize(),
            resourceUiState = state.exchangeRateState,
            successView = { exchangeRateState ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    items(exchangeRateState.exchangeRateList) { exchangeRate ->
                        ExchangeRateItem(
                            item = exchangeRate,
                            isFirst = exchangeRateState.exchangeRateList.first() == exchangeRate,
                            buyOrSell = buyOrSell,
                            inputText = exchangeRateState.inputText,
                            currencies = exchangeRateState.currencies,
                            onClick = {
                                exchangeRateViewModel.setEvent(
                                    ExchangeRateContract.Event.OnExchangeClick(
                                        exchangeRate.id
                                    )
                                )
                            }
                        )
                    }
                }
            },
            onTryAgain = { exchangeRateViewModel.setEvent(ExchangeRateContract.Event.OnTryCheckAgainClick) },
            onCheckAgain = { exchangeRateViewModel.setEvent(ExchangeRateContract.Event.OnTryCheckAgainClick) },
        )
    }
}