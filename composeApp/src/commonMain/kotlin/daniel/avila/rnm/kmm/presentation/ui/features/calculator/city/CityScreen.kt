package daniel.avila.rnm.kmm.presentation.ui.features.calculator.city

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.presentation.state.ManagementResourceUiState
import daniel.avila.rnm.kmm.presentation.ui.common.ButtonBlue

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
            val selectedOptionCity = remember { mutableStateOf(cities.first()) }

            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.background(Color.White)
            ) {
                Text(
                    text = "Местоположение",
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(
                        top = 10.dp,
                        bottom = 10.dp,
                        start = 30.dp,
                        end = 30.dp
                    )
                )

                LazyColumn(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
                        .fillMaxHeight(0.8f)
                ) {
                    items(cities) { city ->
                        CityItem(city = city, selectedOptionCity)
                    }
                }

                ButtonBlue(
                    buttonText = "Сохранить",
                    modifier = Modifier.padding(
                        top = 10.dp,
                        bottom = 40.dp,
                        start = 20.dp,
                        end = 20.dp
                    ),
                    onClick = {
                        onCitySelected(selectedOptionCity.value)
                    }
                )
            }
        },
        onTryAgain = { cityViewModel.setEvent(CityContract.Event.OnTryCheckAgainClick) },
        onCheckAgain = { cityViewModel.setEvent(CityContract.Event.OnTryCheckAgainClick) },
    )
}
