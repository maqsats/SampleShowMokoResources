package daniel.avila.rnm.kmm.presentation.ui.features.calculator.city

import daniel.avila.rnm.kmm.domain.model.city.City
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.mvi.UiEffect
import daniel.avila.rnm.kmm.presentation.mvi.UiEvent
import daniel.avila.rnm.kmm.presentation.mvi.UiState

interface CityContract {

    sealed interface Event : UiEvent {
        data object OnTryCheckAgainClick : Event
        data object OnCityIconClick : Event
        data class OnCityChosen(val city: City) : Event
    }

    data class State(
        val cities: ResourceUiState<List<City>>,
        val selectedCity: City? = null
    ) : UiState

    sealed interface Effect : UiEffect {
        data object ShowCitySelectionBottomSheet : Effect
        data class OnCitySelected(val city: City) : Effect
    }
}


