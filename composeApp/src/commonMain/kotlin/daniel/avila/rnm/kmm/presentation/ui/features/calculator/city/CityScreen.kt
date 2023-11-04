package daniel.avila.rnm.kmm.presentation.ui.features.calculator.city

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
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
        modifier = modifier,
        resourceUiState = state.cities,
        successView = { cities ->
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.background(Color.White)
            ) {
                Text(
                    text = "Выберите ваш город",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                        .padding(top = 10.dp, bottom = 10.dp, start = 25.dp, end = 25.dp)
                )
                LazyColumn(
                    modifier = modifier.fillMaxSize().padding(vertical = 10.dp, horizontal = 15.dp)
                ) {
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
