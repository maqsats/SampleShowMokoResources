package daniel.avila.rnm.kmm.presentation.ui.features.exchange_places

import cafe.adriel.voyager.core.model.coroutineScope
import daniel.avila.rnm.kmm.domain.interactors.national_bank.GetNationalBankUseCase
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.launch

class AllPlacesViewModel(
    private val getNationalBankUseCase: GetNationalBankUseCase
) : BaseViewModel<AllPlacesContract.Event, AllPlacesContract.State, AllPlacesContract.Effect>() {


    init {
        getCurrencyList()
    }

    override fun createInitialState(): AllPlacesContract.State =
        AllPlacesContract.State(currencyList = ResourceUiState.Idle, exchangerList = ResourceUiState.Idle)

    override fun handleEvent(event: AllPlacesContract.Event) {
        when (event) {
            is AllPlacesContract.Event.OnTryCheckAgainClick -> getCurrencyList()
        }
    }

    private fun getCurrencyList() {
        setState { copy(currencyList = ResourceUiState.Loading) }
        coroutineScope.launch {
            getNationalBankUseCase(Unit)
                .onSuccess {
                    setState {
                        copy(
                            currencyList = if (it.isEmpty())
                                ResourceUiState.Empty
                            else ResourceUiState.Success(it)
                        )
                    }
                }
                .onFailure { setState { copy(currencyList = ResourceUiState.Error()) } }
        }
    }
}
