package com.dna.payments.kmm.presentation.ui.features.online_payments.detail

import cafe.adriel.voyager.core.model.screenModelScope
import com.dna.payments.kmm.domain.interactors.use_cases.online_payments.GetTransactionByIdUseCase
import com.dna.payments.kmm.domain.model.online_payments.OnlinePaymentOperationType
import com.dna.payments.kmm.domain.model.online_payments.OnlinePaymentStatus
import com.dna.payments.kmm.domain.model.online_payments.ProcessTypeId
import com.dna.payments.kmm.domain.model.transactions.Transaction
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.launch

class DetailOnlinePaymentViewModel(
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
) : BaseViewModel<DetailOnlinePaymentContract.Event, DetailOnlinePaymentContract.State, DetailOnlinePaymentContract.Effect>() {

    override fun createInitialState(): DetailOnlinePaymentContract.State =
        DetailOnlinePaymentContract.State(
            detailPaymentState = ResourceUiState.Loading,
            operationTypeList = emptyList(),
            isReceiptVisible = false
        )

    override fun handleEvent(event: DetailOnlinePaymentContract.Event) {
        when (event) {
            is DetailOnlinePaymentContract.Event.OnTransactionChanged -> {
                getTransaction(event.transactionId)
            }

            is DetailOnlinePaymentContract.Event.OnTransactionGet -> {
                updateOperationTypeList(event.transaction)
                screenModelScope.launch {
                    setState {
                        copy(detailPaymentState = ResourceUiState.Success(event.transaction))
                    }
                }
            }
        }
    }

    private fun updateOperationTypeList(transaction: Transaction?) {
        if (transaction == null) return
        val tempOperationTypeList = mutableListOf<OnlinePaymentOperationType>()
        if (transaction.status == OnlinePaymentStatus.AUTH) {
            tempOperationTypeList.add(OnlinePaymentOperationType.CHARGE)
            tempOperationTypeList.add(OnlinePaymentOperationType.CANCEL)
        }

        if ((transaction.status == OnlinePaymentStatus.CHARGE) && (transaction.balance > 0)) {
            tempOperationTypeList.add(OnlinePaymentOperationType.REFUND)
        }

        if (isProcessNewPaymentActionVisible(transaction.processingTypeId, transaction.status)) {
            tempOperationTypeList.add(OnlinePaymentOperationType.PROCESS_NEW_PAYMENT)
        }

        setState {
            copy(
                operationTypeList = tempOperationTypeList,
                isReceiptVisible = (transaction.status == OnlinePaymentStatus.CHARGE)
                        || (transaction.status == OnlinePaymentStatus.AUTH)
                        || (transaction.status == OnlinePaymentStatus.CREDITED)
            )
        }
    }

    private fun isProcessNewPaymentActionVisible(
        processingTypeId: Int, status: OnlinePaymentStatus
    ): Boolean {
        return (validProcessingTypeIdsForProcessNewPayment.contains(
            ProcessTypeId.fromInt(
                processingTypeId
            )
        ) && status !in listOf(
            OnlinePaymentStatus.REJECT,
            OnlinePaymentStatus.FAILED,
            OnlinePaymentStatus.PROCESSING,
            OnlinePaymentStatus.NEW
        ))
    }

    private fun getTransaction(transactionId: String) {
        setState { copy(detailPaymentState = ResourceUiState.Loading) }
        screenModelScope.launch {
            val result = getTransactionByIdUseCase(transactionId = transactionId)
            setState {
                copy(
                    detailPaymentState = when (result) {
                        is Response.Success -> {
                            updateOperationTypeList(result.data.records?.first())
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

    companion object {
        val validProcessingTypeIdsForProcessNewPayment = listOf(
            ProcessTypeId.InitialUCOF,
            ProcessTypeId.InitialCOF,
            ProcessTypeId.InitialRecurring,
            ProcessTypeId.MailOrderInitialCOF,
            ProcessTypeId.TelephoneOrderInitialCOF,
            ProcessTypeId.MOTORecurringMIT
        )
    }
}
