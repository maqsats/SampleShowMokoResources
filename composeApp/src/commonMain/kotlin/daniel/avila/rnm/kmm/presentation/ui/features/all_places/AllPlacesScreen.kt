package daniel.avila.rnm.kmm.presentation.ui.features.all_places

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import daniel.avila.rnm.kmm.domain.params.ExchangerParameters
import daniel.avila.rnm.kmm.presentation.ui.common.LocalSelectedCity
import daniel.avila.rnm.kmm.presentation.ui.features.all_places.exchangers.ExchangersScreen
import daniel.avila.rnm.kmm.presentation.ui.features.all_places.national_bank.NationalBankScreen

class ExchangePlaces(val modifier: Modifier = Modifier) : Screen {

    @Composable
    override fun Content() {

        val allPlacesViewModel = getScreenModel<AllPlacesViewModel>()

        val state by allPlacesViewModel.uiState.collectAsState()

        val selectedCity = LocalSelectedCity.current

        LaunchedEffect(selectedCity) {

            if (selectedCity == null) return@LaunchedEffect
            if (allPlacesViewModel.effect is AllPlacesContract.Effect) return@LaunchedEffect
            allPlacesViewModel.setEvent(
                AllPlacesContract.Event.OnFetchData(
                    param = ExchangerParameters(
                        cityId = selectedCity.id,
                        lat = 43.238949,
                        lng = 76.889709
                    )
                )
            )
        }

        LazyColumn(modifier = modifier.fillMaxSize()) {
            item {
                NationalBankScreen(state.nationalBankCurrencyList)
            }
            item {
                ExchangersScreen(state.exchangerList, allPlacesViewModel)
            }
        }
    }
}