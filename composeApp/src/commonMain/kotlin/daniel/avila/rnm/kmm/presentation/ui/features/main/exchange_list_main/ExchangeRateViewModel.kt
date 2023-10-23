package daniel.avila.rnm.kmm.presentation.ui.features.main.exchange_list_main

import cafe.adriel.voyager.core.model.coroutineScope
import daniel.avila.rnm.kmm.domain.interactors.GetExchangeRateUseCase
import daniel.avila.rnm.kmm.domain.params.ExchangeRateParameters
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.launch

class ExchangeRateViewModel(
    private val getExchangeRateUseCase: GetExchangeRateUseCase
) : BaseViewModel<ExchangeRateContract.Event, ExchangeRateContract.State, ExchangeRateContract.Effect>() {

    lateinit var param: ExchangeRateParameters

    override fun createInitialState(): ExchangeRateContract.State =
        ExchangeRateContract.State(ResourceUiState.Idle)

    override fun handleEvent(event: ExchangeRateContract.Event) {
        when (event) {
            is ExchangeRateContract.Event.OnTryCheckAgainClick -> getExchangeRates(param)
            is ExchangeRateContract.Event.OnExchangeClick -> setEffect {
                ExchangeRateContract.Effect.NavigateToDetailExchangeRate(
                    event.idExchangeRate
                )
            }
            is ExchangeRateContract.Event.OnFetchData -> {
                getExchangeRates(event.param)
            }
        }
    }

    private fun getExchangeRates(param: ExchangeRateParameters) {
        setState { copy(exchangeRateList = ResourceUiState.Loading) }
        coroutineScope.launch {
            getExchangeRateUseCase(param)
                .onSuccess {
                    setState {
                        copy(
                            exchangeRateList = if (it.isEmpty())
                                ResourceUiState.Empty
                            else
                                ResourceUiState.Success(it)
                        )
                    }
                }
                .onFailure { setState { copy(exchangeRateList = ResourceUiState.Error()) } }
        }
    }
}
