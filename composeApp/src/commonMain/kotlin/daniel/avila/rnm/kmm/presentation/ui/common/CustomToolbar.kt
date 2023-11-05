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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.MR
import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.city.CityContract
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.city.CityScreen
import daniel.avila.rnm.kmm.presentation.ui.features.calculator.city.CityViewModel
import daniel.avila.rnm.kmm.presentation.ui.features.home.bottom_nav.BottomBarRoute
import dev.icerock.moko.resources.compose.painterResource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


val LocalSelectedCity = compositionLocalOf<City?> { null }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomToolbar(
    modifier: Modifier = Modifier,
    bottomBarRoute: BottomBarRoute,
    cityState: MutableState<City?>
) {

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    val scope = rememberCoroutineScope()

    val cityViewModel = koinInject<CityViewModel>()

    val state by cityViewModel.uiState.collectAsState()

    LaunchedEffect(state.selectedCity) {
        cityState.value = state.selectedCity
    }

    LaunchedEffect(key1 = Unit) {
        cityViewModel.effect.collectLatest { effect ->
            when (effect) {
                is CityContract.Effect.OnCitySelected -> {
                    cityState.value = effect.city
                }
                CityContract.Effect.ShowCitySelectionBottomSheet -> {
                    openBottomSheet = true
                }
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