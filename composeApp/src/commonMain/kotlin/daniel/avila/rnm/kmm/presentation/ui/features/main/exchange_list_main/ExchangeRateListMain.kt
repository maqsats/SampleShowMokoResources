package daniel.avila.rnm.kmm.presentation.ui.features.main.exchange_list_main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.domain.model.exchange_rate.BuyOrSell
import daniel.avila.rnm.kmm.domain.params.ExchangeRateParameters
import daniel.avila.rnm.kmm.presentation.state.ManagementResourceUiState
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
import org.koin.compose.koinInject

@Composable
fun ExchangeRateListMain(
    modifier: Modifier,
    buyOrSell: BuyOrSell,
    text: String,
    currencies: Pair<Currency, Currency>?
) {

    println("ExchangeRateListMain")
    val exchangeRateViewModel = koinInject<ExchangeRateViewModel>()

    val state by exchangeRateViewModel.uiState.collectAsState()

    val navigator = LocalNavigator.currentOrThrow

    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController =
        remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)

    val locationTracker = rememberLocationTrackerFactory(accuracy = LocationTrackerAccuracy.Best)
        .createLocationTracker(controller)

    BindLocationTrackerEffect(locationTracker)
//
//    LaunchedEffect(key1 = null) {
//        scope.launch {
//            try {
//                controller.providePermission(Permission.LOCATION)
//                locationTracker.startTracking()
//                locationTracker.getLocationsFlow().collect {
//                    exchangeRateViewModel.setEvent(
//                        ExchangeRateContract.Event.OnFetchData(
//                            param = ExchangeRateParameters(
//                                cityId = 1,
//                                lat = 43.238949,
//                                lng = 76.889709,
//                                buyOrSell = buyOrSell.value,
//                                currencyCode = "USD"
//                            )
//                        )
//                    )
//                    locationTracker.stopTracking()
//                }
//            } catch (deniedAlways: DeniedAlwaysException) {
//                println("permissions denied always")
//            } catch (denied: DeniedException) {
//                println("permissions denied")
//            }
//        }
//    }

    LaunchedEffect(text) {
        if (text.isEmpty()) return@LaunchedEffect
        exchangeRateViewModel.setEvent(
            ExchangeRateContract.Event.OnInputValueChange(
                inputText = text
            )
        )
    }

    LaunchedEffect(currencies) {

        if (currencies == null) return@LaunchedEffect

        exchangeRateViewModel.setEvent(
            ExchangeRateContract.Event.OnFetchData(
                param = ExchangeRateParameters(
                    cityId = 1,
                    lat = 43.238949,
                    lng = 76.889709,
                    buyOrSell = buyOrSell.value,
                    currencyCode = currencies.second.code
                ),
                inputText = text,
                currencies = currencies
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
            ExchangeRateList(
                exchangeRateList = exchangeRateState.exchangeRateList,
                buyOrSell = buyOrSell,
                inputText = exchangeRateState.inputText,
                currencies = exchangeRateState.currencies,
            ) { idExchangeRate ->
                exchangeRateViewModel.setEvent(
                    ExchangeRateContract.Event.OnExchangeClick(
                        idExchangeRate
                    )
                )
            }
        },
        onTryAgain = { exchangeRateViewModel.setEvent(ExchangeRateContract.Event.OnTryCheckAgainClick) },
        onCheckAgain = { exchangeRateViewModel.setEvent(ExchangeRateContract.Event.OnTryCheckAgainClick) },
    )
}