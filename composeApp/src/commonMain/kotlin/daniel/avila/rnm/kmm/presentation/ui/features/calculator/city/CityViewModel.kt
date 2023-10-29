package daniel.avila.rnm.kmm.presentation.ui.features.calculator.city

import cafe.adriel.voyager.core.model.coroutineScope
import daniel.avila.rnm.kmm.data.preferences.Preferences
import daniel.avila.rnm.kmm.domain.interactors.city.GetCityUseCase
import daniel.avila.rnm.kmm.domain.params.CityParameters
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.launch

class CityViewModel(
    private val preferences: Preferences,
    private val getCityUseCase: GetCityUseCase,
) : BaseViewModel<CityContract.Event, CityContract.State, CityContract.Effect>() {


    init {
        val city = preferences.getCity()
        if (city != null) {
            setState { copy(selectedCity = city) }
        } else {
            setEffect { CityContract.Effect.ShowCitySelectionBottomSheet }
            getCities()
        }
    }

    override fun createInitialState(): CityContract.State =
        CityContract.State(cities = ResourceUiState.Idle)

    override fun handleEvent(event: CityContract.Event) {
        when (event) {
            is CityContract.Event.OnTryCheckAgainClick -> getCities()
            is CityContract.Event.OnCityChosen -> {
                preferences.setCity(event.city)
                setEffect { CityContract.Effect.OnCitySelected(event.city) }
            }
            is CityContract.Event.OnCityIconClick -> {
                setEffect { CityContract.Effect.ShowCitySelectionBottomSheet }
                getCities()
            }
        }
    }

    private fun getCities() {
        setState { copy(cities = ResourceUiState.Loading) }
        coroutineScope.launch {
            getCityUseCase(CityParameters(COUNTRY_ID))
                .onSuccess {
                    setState {
                        copy(
                            cities = if (it.isEmpty())
                                ResourceUiState.Empty
                            else ResourceUiState.Success(it)
                        )
                    }
                }
                .onFailure { setState { copy(cities = ResourceUiState.Error()) } }
        }
    }

    companion object {
        private const val COUNTRY_ID = 1
    }
}
