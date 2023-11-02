package daniel.avila.rnm.kmm.presentation.ui.features.all_places

import cafe.adriel.voyager.core.model.coroutineScope
import daniel.avila.rnm.kmm.domain.interactors.national_bank.GetNationalBankCurrencyUseCase
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.launch

class AllPlacesViewModel(
    private val getNationalBankCurrencyUseCase: GetNationalBankCurrencyUseCase
) : BaseViewModel<AllPlacesContract.Event, AllPlacesContract.State, AllPlacesContract.Effect>() {


    init {
        getCurrencyList()
    }

    override fun createInitialState(): AllPlacesContract.State =
        AllPlacesContract.State(nationalBankCurrencyList = ResourceUiState.Idle, exchangerList = ResourceUiState.Idle)

    override fun handleEvent(event: AllPlacesContract.Event) {
        when (event) {
            is AllPlacesContract.Event.OnTryCheckAgainClick -> getCurrencyList()
        }
    }

    private fun getCurrencyList() {
        setState { copy(nationalBankCurrencyList = ResourceUiState.Loading) }
        coroutineScope.launch {
            getNationalBankCurrencyUseCase(Unit)
                .onSuccess {
                    println(it)
                    setState {
                        copy(
                            nationalBankCurrencyList = if (it.isEmpty())
                                ResourceUiState.Empty
                            else ResourceUiState.Success(it)
                        )
                    }
                }
                .onFailure { setState { copy(nationalBankCurrencyList = ResourceUiState.Error()) } }
        }
    }
}
