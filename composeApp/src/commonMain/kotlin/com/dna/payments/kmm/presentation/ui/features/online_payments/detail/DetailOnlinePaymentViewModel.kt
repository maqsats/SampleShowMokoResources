package com.dna.payments.kmm.presentation.ui.features.online_payments.detail

import cafe.adriel.voyager.core.model.screenModelScope
import com.dna.payments.kmm.domain.interactors.use_cases.online_payments.GetTransactionByIdUseCase
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailOnlinePaymentViewModel(
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase
) : BaseViewModel<DetailOnlinePaymentContract.Event, DetailOnlinePaymentContract.State, DetailOnlinePaymentContract.Effect>() {

    override fun createInitialState(): DetailOnlinePaymentContract.State =
        DetailOnlinePaymentContract.State(
            detailPaymentState = ResourceUiState.Loading
        )

    override fun handleEvent(event: DetailOnlinePaymentContract.Event) {
        when (event) {
            is DetailOnlinePaymentContract.Event.OnTransactionChanged -> {
                getTransaction(event.transactionId)
            }

            is DetailOnlinePaymentContract.Event.OnTransactionGet -> {
                screenModelScope.launch {
                    setState {
                        copy(detailPaymentState = ResourceUiState.Success(event.transaction))
                    }
                }
            }
        }
    }

    private fun getTransaction(transactionId: String) {
        setState { copy(detailPaymentState = ResourceUiState.Loading) }
        screenModelScope.launch {
            val result = getTransactionByIdUseCase(transactionId = transactionId)
            setState {
                copy(
                    detailPaymentState = when (result) {
                        is Response.Success -> {
                            ResourceUiState.Success(result.data.records?.first())
                        }

                        is Response.Error -> {
                            ResourceUiState.Error(result.error)
                        }

                        is Response.NetworkError -> {
                            ResourceUiState.NetworkError
                        }

                        is Response.TokenExpire -> {
                            ResourceUiState.TokenExpire
                        }
                    }
                )
            }
        }
    }
}
