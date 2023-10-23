package daniel.avila.rnm.kmm.presentation.ui.features.main.exchange_list_main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.MR
import daniel.avila.rnm.kmm.domain.params.ExchangeRateParameters
import daniel.avila.rnm.kmm.presentation.state.ManagementResourceUiState
import daniel.avila.rnm.kmm.presentation.ui.common.LocalBottomSheetNavigator
import daniel.avila.rnm.kmm.presentation.ui.common.RoundedBackground
import daniel.avila.rnm.kmm.presentation.ui.features.main.custom_main_tab.TabItem
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
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun ExchangeListMain(modifier: Modifier) {
    val bottomSheetNavigator = LocalBottomSheetNavigator.current

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

    val list = listOf(
        TabItem("Ближайший", true),
        TabItem("Популярный", false)
    )

    var selectedItem by remember { mutableStateOf(list.first()) }

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
                                lat = 43.238949, //43.238949/76.889709
                                lng = 76.889709,
                                buyOrSell = "buy",
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

    LaunchedEffect(key1 = Unit) {
        exchangeRateViewModel.effect.collectLatest { effect ->
            when (effect) {
                is ExchangeRateContract.Effect.NavigateToDetailExchangeRate -> {
                    navigator.push(Screen2())
                }
            }
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(MR.images.bank),
                modifier = Modifier.width(20.dp).height(20.dp),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                text = stringResource(MR.strings.course_in_the_city),
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(MR.images.menu),
                modifier = Modifier.width(20.dp).height(20.dp).clickable {
                    scope.launch {
                        bottomSheetNavigator.show(BottomSheet())
                    }
                },
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(10.dp))


        RoundedBackground(
            modifier = Modifier.wrapContentWidth().padding(start = 15.dp),
            backgroundColor = MaterialTheme.colors.secondary,
            border = 45.dp,
            height = 22.dp,
            onClick = {

            }
        ) {
            Text(
                text = "Самый выгодный",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSecondary,
            )
        }


        ManagementResourceUiState(
            modifier = Modifier
                .fillMaxWidth().wrapContentHeight(),
            resourceUiState = state.exchangeRateList,
            successView = { exchangeRateList ->
                ExchangeRateItem(
                    item = exchangeRateList.first(),
                    onClick = { idExchangeRate ->
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

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 15.dp),
        ) {
            list.forEach { tabItem ->
                val isSelected = selectedItem == tabItem
                RoundedBackground(
                    modifier = Modifier.wrapContentWidth(),
                    backgroundColor = if (isSelected) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.secondary,
                    border = 45.dp,
                    height = 22.dp,
                    onLongClick = {
                        selectedItem = tabItem
                    },
                    onClick = {
                        selectedItem = tabItem
                    }
                ) {
                    Text(
                        text = tabItem.stringResId,
                        style = MaterialTheme.typography.h5,
                        color = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSecondary,
                    )
                }

                if (tabItem != list.last()) {
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }


        ManagementResourceUiState(
            modifier = Modifier
                .fillMaxWidth().wrapContentHeight(),
            resourceUiState = state.exchangeRateList,
            successView = { exchangeRateList ->
                ExchangeRateList(
                    exchangeRateList = exchangeRateList,
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
}