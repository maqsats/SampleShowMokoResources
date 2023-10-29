package daniel.avila.rnm.kmm.presentation.ui.features.all_places

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import daniel.avila.rnm.kmm.presentation.ui.features.all_places.exchangers.ExchangersScreen
import daniel.avila.rnm.kmm.presentation.ui.features.all_places.national_bank.NationalBankScreen
import org.koin.compose.koinInject

@Composable
fun ExchangePlaces(modifier: Modifier = Modifier) {


    val allPlacesViewModel = koinInject<AllPlacesViewModel>()

    val state by allPlacesViewModel.uiState.collectAsState()


    LazyColumn(modifier = modifier.fillMaxWidth()) {
        item {
            NationalBankScreen(state.nationalBankCurrencyList)
        }
        item {
            ExchangersScreen(state.exchangerList, allPlacesViewModel)
        }
    }
}