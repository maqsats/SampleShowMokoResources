package daniel.avila.rnm.kmm.presentation.ui.features.calculator.currency

import cafe.adriel.voyager.core.model.coroutineScope
import daniel.avila.rnm.kmm.domain.interactors.currency.GetCurrencyUseCase
import daniel.avila.rnm.kmm.domain.model.currency.Currency
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.launch

class CurrencyViewModel(
    private val getCurrencyUseCase: GetCurrencyUseCase,
) : BaseViewModel<CurrencyContract.Event, CurrencyContract.State, CurrencyContract.Effect>() {

    private lateinit var currencyList: List<Currency>

    init {
        getCurrencies()
    }

    override fun createInitialState(): CurrencyContract.State =
        CurrencyContract.State(currencies = ResourceUiState.Idle)

    override fun handleEvent(event: CurrencyContract.Event) {
        when (event) {
            CurrencyContract.Event.OnCurrencyClick -> {
                if (!::currencyList.isInitialized) return
                setEffect {
                    CurrencyContract.Effect.NavigateToCurrencyDialog(
                        currencyList
                    )
                }
            }
            CurrencyContract.Event.OnTryCheckAgainClick -> {
                getCurrencies()
            }
        }
    }

    private fun getCurrencies() {
        setState { copy(currencies = ResourceUiState.Loading) }
        coroutineScope.launch {
            getCurrencyUseCase(Unit)
                .onSuccess {
                    setState {
                        copy(
                            currencies = if (it.isEmpty())
                                ResourceUiState.Empty
                            else {
                                currencyList = it
                                ResourceUiState.Success(it)
                            }
                        )
                    }
                }
                .onFailure { setState { copy(currencies = ResourceUiState.Error()) } }
        }
    }
}
