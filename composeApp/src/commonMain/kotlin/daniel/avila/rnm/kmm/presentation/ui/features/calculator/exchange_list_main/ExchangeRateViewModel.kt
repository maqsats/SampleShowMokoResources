package daniel.avila.rnm.kmm.presentation.ui.features.calculator.exchange_list_main

import cafe.adriel.voyager.core.model.coroutineScope
import daniel.avila.rnm.kmm.domain.interactors.exchange_rate.GetExchangeRateUseCase
import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.domain.model.exchange_rate.ExchangeRate
import daniel.avila.rnm.kmm.domain.params.ExchangeRateParameters
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.launch

class ExchangeRateViewModel(
    private val getExchangeRateUseCase: GetExchangeRateUseCase
) : BaseViewModel<ExchangeRateContract.Event, ExchangeRateContract.State, ExchangeRateContract.Effect>() {

    private lateinit var localList: List<ExchangeRate>
    private lateinit var currencies: Pair<Currency, Currency>
    private lateinit var inputText: String
    private lateinit var param: ExchangeRateParameters

    override fun createInitialState(): ExchangeRateContract.State =
        ExchangeRateContract.State(ResourceUiState.Idle)

    override fun handleEvent(event: ExchangeRateContract.Event) {
        when (event) {
            is ExchangeRateContract.Event.OnTryCheckAgainClick -> getExchangeRates(
                param
            )
            is ExchangeRateContract.Event.OnExchangeClick -> setEffect {
                ExchangeRateContract.Effect.NavigateToDetailExchangeRate(
                    event.idExchangeRate
                )
            }
            is ExchangeRateContract.Event.OnFetchData -> {
                if (::param.isInitialized && param == event.param) return
                param = event.param
                currencies = event.currencies
                inputText = event.inputText
                getExchangeRates(event.param)
            }
            is ExchangeRateContract.Event.OnInputValueChange -> {
                if (!this::localList.isInitialized) return
                setState {
                    copy(
                        exchangeRateState = ResourceUiState.Success(
                            ExchangeRateState(
                                localList,
                                event.inputText,
                                currencies
                            )
                        )
                    )
                }
            }
        }
    }

    private fun getExchangeRates(
        param: ExchangeRateParameters
    ) {
        setState { copy(exchangeRateState = ResourceUiState.Loading) }
        coroutineScope.launch {
            getExchangeRateUseCase(param)
                .onSuccess {
                    setState {
                        copy(
                            exchangeRateState = if (it.isEmpty())
                                ResourceUiState.Empty
                            else {
                                localList = it
                                ResourceUiState.Success(
                                    ExchangeRateState(
                                        exchangeRateList = localList,
                                        inputText,
                                        currencies
                                    )
                                )
                            }
                        )
                    }
                }
                .onFailure { setState { copy(exchangeRateState = ResourceUiState.Error()) } }
        }
    }
}
