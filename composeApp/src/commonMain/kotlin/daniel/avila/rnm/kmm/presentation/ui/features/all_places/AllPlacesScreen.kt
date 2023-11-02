package daniel.avila.rnm.kmm.presentation.ui.features.all_places

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.domain.params.ExchangerParameters
import daniel.avila.rnm.kmm.presentation.ui.features.all_places.exchangers.ExchangersScreen
import daniel.avila.rnm.kmm.presentation.ui.features.all_places.national_bank.NationalBankScreen
import org.koin.compose.koinInject

@Composable
fun ExchangePlaces(modifier: Modifier = Modifier, city: City?) {


    val allPlacesViewModel = koinInject<AllPlacesViewModel>()

    val state by allPlacesViewModel.uiState.collectAsState()

    LaunchedEffect(city) {
        println("city = $city")
        if (city == null) return@LaunchedEffect

        allPlacesViewModel.setEvent(
            AllPlacesContract.Event.OnFetchData(
                param = ExchangerParameters(
                    cityId = city.id,
                    lat = 43.238949,
                    lng = 76.889709
                )
            )
        )
    }

    Column(modifier = modifier.fillMaxSize()) {

        NationalBankScreen(state.nationalBankCurrencyList)

        ExchangersScreen(state.exchangerList, allPlacesViewModel)
    }
}