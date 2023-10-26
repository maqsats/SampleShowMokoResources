package daniel.avila.rnm.kmm.presentation.ui.features.main.exchange_list_main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import daniel.avila.rnm.kmm.domain.model.exchange_rate.BuyOrSell
import daniel.avila.rnm.kmm.domain.params.ExchangeRateParameters
import daniel.avila.rnm.kmm.presentation.state.ManagementResourceUiState
import daniel.avila.rnm.kmm.presentation.ui.features.screen2.Screen2
import daniel.avila.rnm.kmm.utils.maps.geo.BindLocationTrackerEffect
import daniel.avila.rnm.kmm.utils.maps.geo.LocationTrackerAccuracy
import daniel.avila.rnm.kmm.utils.maps.geo.rememberLocationTrackerFactory
import daniel.avila.rnm.kmm.utils.navigation.LocalNavigator
import daniel.avila.rnm.kmm.utils.navigation.currentOrThrow
import daniel.avila.rnm.kmm.utils.permissions.DeniedAlwaysException
import daniel.avila.rnm.kmm.utils.permissions.DeniedException
import daniel.avila.rnm.kmm.utils.permissions.Permission
import daniel.avila.rnm.kmm.utils.permissions.PermissionsController
import daniel.avila.rnm.kmm.utils.permissions.compose.BindEffect
import daniel.avila.rnm.kmm.utils.permissions.compose.PermissionsControllerFactory
import daniel.avila.rnm.kmm.utils.permissions.compose.rememberPermissionsControllerFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun ExchangeRateListMain(modifier: Modifier, buyOrSell: BuyOrSell) {

    val exchangeRateViewModel = koinInject<ExchangeRateViewModel>()

    val scope = rememberCoroutineScope()

    val state by exchangeRateViewModel.uiState.collectAsState()

    val navigator = LocalNavigator.currentOrThrow

    val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
    val controller: PermissionsController =
        remember(factory) { factory.createPermissionsController() }
    BindEffect(controller)

    val locationTracker = rememberLocationTrackerFactory(accuracy = LocationTrackerAccuracy.Best)
        .createLocationTracker(controller)

    BindLocationTrackerEffect(locationTracker)

    LaunchedEffect(key1 = null) {
        scope.launch {
            try {
                controller.providePermission(Permission.LOCATION)
                locationTracker.startTracking()
                locationTracker.getLocationsFlow().collect {
                    exchangeRateViewModel.setEvent(
                        ExchangeRateContract.Event.OnFetchData(
                            param = ExchangeRateParameters(
                                cityId = 1,
                                lat = 43.238949,
                                lng = 76.889709,
                                buyOrSell = buyOrSell.value,
                                currencyCode = "USD"
                            )
                        )
                    )
                    locationTracker.stopTracking()
                }
            } catch (deniedAlways: DeniedAlwaysException) {
                println("permissions denied always")
            } catch (denied: DeniedException) {
                println("permissions denied")
            }
        }
    }

    LaunchedEffect(buyOrSell) {
        println("buyOrSell = ${buyOrSell.value}")
        exchangeRateViewModel.setEvent(
            ExchangeRateContract.Event.OnFetchData(
                param = ExchangeRateParameters(
                    cityId = 1,
                    lat = 43.238949,
                    lng = 76.889709,
                    buyOrSell = buyOrSell.value,
                    currencyCode = "USD"
                )
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
        resourceUiState = state.exchangeRateList,
        successView = { exchangeRateList ->
            ExchangeRateList(
                exchangeRateList = exchangeRateList,
                buyOrSell = buyOrSell,
                onExchangeRateClick = { idExchangeRate ->
                    exchangeRateViewModel.setEvent(
                        ExchangeRateContract.Event.OnExchangeClick(
                            idExchangeRate
                        )
                    )
                }
            )
        },
        onTryAgain = { exchangeRateViewModel.setEvent(ExchangeRateContract.Event.OnTryCheckAgainClick) },
        onCheckAgain = { exchangeRateViewModel.setEvent(ExchangeRateContract.Event.OnTryCheckAgainClick) },
    )
}