package daniel.avila.rnm.kmm.presentation.ui.features.all_places

import cafe.adriel.voyager.core.model.coroutineScope
import daniel.avila.rnm.kmm.domain.interactors.exchanger.GetExchangerUseCase
import daniel.avila.rnm.kmm.domain.interactors.national_bank.GetNationalBankCurrencyUseCase
import daniel.avila.rnm.kmm.domain.params.ExchangerParameters
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.launch

class AllPlacesViewModel(
    private val getNationalBankCurrencyUseCase: GetNationalBankCurrencyUseCase,
    private val getExchangerUseCase: GetExchangerUseCase
) : BaseViewModel<AllPlacesContract.Event, AllPlacesContract.State, AllPlacesContract.Effect>() {

    private lateinit var exchangerParameters: ExchangerParameters

    init {
        getNBCurrencyList()
    }

    override fun createInitialState(): AllPlacesContract.State =
        AllPlacesContract.State(
            nationalBankCurrencyList = ResourceUiState.Idle,
            exchangerList = ResourceUiState.Idle
        )

    override fun handleEvent(event: AllPlacesContract.Event) {
        when (event) {
            is AllPlacesContract.Event.OnTryCheckAgainClick -> {
                getNBCurrencyList()
                if (::exchangerParameters.isInitialized) getExchangerList()
            }
            is AllPlacesContract.Event.OnFetchData -> {
                if (::exchangerParameters.isInitialized && exchangerParameters == event.param) return
                exchangerParameters = event.param
                getExchangerList()
            }
        }
    }

    private fun getNBCurrencyList() {
        setState { copy(nationalBankCurrencyList = ResourceUiState.Loading) }
        coroutineScope.launch {
            getNationalBankCurrencyUseCase(Unit)
                .onSuccess {
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

    private fun getExchangerList() {
        setState { copy(exchangerList = ResourceUiState.Loading) }
        coroutineScope.launch {
            getExchangerUseCase(exchangerParameters)
                .onSuccess {
                    setState {
                        copy(
                            exchangerList = if (it.isEmpty())
                                ResourceUiState.Empty
                            else ResourceUiState.Success(it)
                        )
                    }
                }
                .onFailure { setState { copy(exchangerList = ResourceUiState.Error()) } }
        }
    }
}
