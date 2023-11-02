package daniel.avila.rnm.kmm.presentation.ui.features.national_bank_currency

import cafe.adriel.voyager.core.model.coroutineScope
import daniel.avila.rnm.kmm.domain.interactors.national_bank.GetNBCurrencyByRangeUseCase
import daniel.avila.rnm.kmm.domain.model.time_period_tab.TimePeriod
import daniel.avila.rnm.kmm.domain.params.CurrencyCode
import daniel.avila.rnm.kmm.presentation.model.ResourceUiState
import daniel.avila.rnm.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.launch

class NBCurrencyViewModel(
    private val getCurrencyByRange: GetNBCurrencyByRangeUseCase
) : BaseViewModel<NBCurrencyContract.Event, NBCurrencyContract.State, NBCurrencyContract.Effect>() {

    private lateinit var param: Pair<TimePeriod, CurrencyCode>

    override fun createInitialState(): NBCurrencyContract.State =
        NBCurrencyContract.State(
            nationalBankCurrencyList = ResourceUiState.Idle
        )

    override fun handleEvent(event: NBCurrencyContract.Event) {
        when (event) {
            is NBCurrencyContract.Event.OnTryCheckAgainClick -> {
                if (!::param.isInitialized)
                    return
            }
            is NBCurrencyContract.Event.OnFetchData -> {
                param = event.nbCurrencyParams
            }
        }
        getCurrencyListByTimePeriod()
    }

    private fun getCurrencyListByTimePeriod() {
        setState { copy(nationalBankCurrencyList = ResourceUiState.Loading) }
        coroutineScope.launch {
            getCurrencyByRange(param)
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
}
