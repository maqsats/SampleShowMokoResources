package daniel.avila.rnm.kmm.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import daniel.avila.rnm.kmm.MR
import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.city.CityContract
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.city.CityScreen
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.city.CityViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.home.bottom_nav.BottomBarRoute
import daniel.avila.rnm.kmm.utils.maps.geo.BindLocationTrackerEffect
import daniel.avila.rnm.kmm.utils.maps.geo.LocationTrackerAccuracy
import daniel.avila.rnm.kmm.utils.maps.geo.rememberLocationTrackerFactory
import daniel.avila.rnm.kmm.utils.permissions.Permission
import daniel.avila.rnm.kmm.utils.permissions.PermissionsController
import daniel.avila.rnm.kmm.utils.permissions.compose.BindEffect
import daniel.avila.rnm.kmm.utils.permissions.compose.PermissionsControllerFactory
import daniel.avila.rnm.kmm.utils.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


val LocalSelectedCity = compositionLocalOf<City?> { null }


class CustomToolbar(
    val modifier: Modifier = Modifier,
    val bottomBarRoute: BottomBarRoute,
    val cityState: MutableState<City?>
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var openBottomSheet by rememberSaveable { mutableStateOf(false) }

        val bottomSheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false
        )

        val scope = rememberCoroutineScope()

        val cityViewModel = getScreenModel<CityViewModel>()

        val state by cityViewModel.uiState.collectAsState()

        //Permission controller
        val factory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
        val controller: PermissionsController =
            remember(factory) { factory.createPermissionsController() }
        BindEffect(controller)

        //Location tracker
        val locationTracker =
            rememberLocationTrackerFactory(accuracy = LocationTrackerAccuracy.Best)
                .createLocationTracker(controller)
        BindLocationTrackerEffect(locationTracker)

        LaunchedEffect(state.selectedCity) {
            if (state.selectedCity == null) return@LaunchedEffect

            val city = state.selectedCity

            if (cityState.value?.id == city?.id) return@LaunchedEffect

            when {
                city == null || !city.isUserLocationUsing -> cityState.value =
                    state.selectedCity
                else -> {
                    cityState.value = city.copy(
                        name = city.name,
                        id = city.id
                    )
                }
            }
        }

        LaunchedEffect(key1 = Unit) {
            scope.launch {
                cityViewModel.effect.collectLatest { effect ->
                    when (effect) {
                        is CityContract.Effect.OnCitySelected -> {
                            val city = cityState.value
                            when {
                                city == null || !city.isUserLocationUsing -> cityState.value =
                                    effect.city
                                else -> {
                                    cityState.value = city.copy(
                                        name = effect.city.name,
                                        id = effect.city.id
                                    )
                                }
                            }
                        }
                        CityContract.Effect.ShowCitySelectionBottomSheet -> {
                            openBottomSheet = true
                        }
                    }
                }
            }

            scope.launch {
                try {
                    println("here")
                    controller.providePermission(Permission.COARSE_LOCATION)
                    if (controller.isPermissionGranted(Permission.COARSE_LOCATION)) {
                        locationTracker.startTracking()
                        locationTracker.getLocationsFlow().distinctUntilChanged()
                            .collect { location ->
                                cityState.value = cityState.value?.copy(
                                    latitude = location.latitude,
                                    longitude = location.longitude,
                                    isUserLocationUsing = true
                                )
                                locationTracker.stopTracking()
                            }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    cityState.value = state.selectedCity
                }
            }
        }

        if (openBottomSheet) {
            BottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = { openBottomSheet = false }
            ) {
                CityScreen(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    state = state,
                    cityViewModel = cityViewModel,
                    city = cityState.value,
                    onCitySelected = {
                        cityViewModel.setEvent(CityContract.Event.OnCityChosen(it))
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) openBottomSheet = false
                        }
                    }
                )
            }
        }

        Row(
            modifier = modifier.fillMaxWidth().height(IntrinsicSize.Min)
                .padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                modifier = Modifier.wrapContentWidth(),
                painter = painterResource(MR.images.tenge_today_logo),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.weight(1f))
            if (bottomBarRoute == BottomBarRoute.EXCHANGE_PLACES) {
                Icon(
                    modifier = Modifier.wrapContentWidth(),
                    painter = painterResource(MR.images.search),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(19.dp))
            }
            Text(
                text = cityState.value?.name.orEmpty(),
                modifier = Modifier.clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) { cityViewModel.setEvent(CityContract.Event.OnCityIconClick) },
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.width(6.dp))
            Icon(
                modifier = Modifier.wrapContentWidth()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ) {
                        cityViewModel.setEvent(CityContract.Event.OnCityIconClick)
                    },
                painter = painterResource(MR.images.location),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}