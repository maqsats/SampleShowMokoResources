package daniel.avila.rnm.kmm.presentation.ui.features.calculator.city

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.presentation.state.ManagementResourceUiState

@Composable
fun CityScreen(
    modifier: Modifier = Modifier,
    state: CityContract.State,
    cityViewModel: CityViewModel,
    onCitySelected: (City) -> Unit
) {
    ManagementResourceUiState(
        modifier = modifier.padding(vertical = 10.dp, horizontal = 15.dp),
        resourceUiState = state.cities,
        successView = { cities ->
            Column(verticalArrangement = Arrangement.Top) {
                Text(
                    text = "Выберите ваш город",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .padding(vertical = 10.dp, horizontal = 10.dp)
                )
                LazyColumn(modifier = modifier.fillMaxSize()) {
                    items(cities) { city ->
                        CityItem(city = city) {
                            onCitySelected(it)
                        }
                    }
                }
            }
        },
        onTryAgain = { cityViewModel.setEvent(CityContract.Event.OnTryCheckAgainClick) },
        onCheckAgain = { cityViewModel.setEvent(CityContract.Event.OnTryCheckAgainClick) },
    )
}
