package com.dna.payments.kmm.presentation.ui.features.online_payments.refund

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.screenModelScope
import com.dna.payments.kmm.domain.interactors.use_cases.online_payments.RefundPaymentOperationUseCase
import com.dna.payments.kmm.domain.interactors.validation.ValidatePaymentAmount
import com.dna.payments.kmm.domain.model.online_payments.OperationType
import com.dna.payments.kmm.domain.network.Response.Companion.onSuccess
import com.dna.payments.kmm.domain.network.toResourceUiState
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.launch

class OnlinePaymentRefundViewModel(
    private val validate: ValidatePaymentAmount,
    private val refundPaymentOperationUseCase: RefundPaymentOperationUseCase
) : BaseViewModel<OnlinePaymentRefundContract.Event, OnlinePaymentRefundContract.State, OnlinePaymentRefundContract.Effect>() {

    override fun createInitialState(): OnlinePaymentRefundContract.State =
        OnlinePaymentRefundContract.State(
            amount = TextFieldUiState(
                input = mutableStateOf(""),
                textInput = TextInput.AMOUNT,
                validationResult = mutableStateOf(ValidationResult(successful = true)),
                onFieldChanged = { this.setEvent(OnlinePaymentRefundContract.Event.OnAmountFieldChanged) }
            ),
            isButtonEnabled = mutableStateOf(false),
            sendReceiptState = ResourceUiState.Idle
        )


    override fun handleEvent(event: OnlinePaymentRefundContract.Event) {
        when (event) {
            OnlinePaymentRefundContract.Event.OnAmountFieldChanged -> {
                with(currentState) {
                    val validateAmountResult = validate(
                        amount = currentState.amount.input.value,
                        balance = 0,
                        textInput = currentState.amount.textInput,
                        operationType = OperationType.REFUND
                    )
                    isButtonEnabled.value = validateAmountResult.successful
                }
            }

            is OnlinePaymentRefundContract.Event.OnRefundClicked -> {
                refund(event.transactionId)
            }
        }
    }

    private fun refund(transactionId: String) {
        setState { copy(sendReceiptState = ResourceUiState.Loading) }
        screenModelScope.launch {
            val result = refundPaymentOperationUseCase(
                transactionId = transactionId,
                amount = currentState.amount.input.value.toInt()
            )
            setState {
                copy(
                    sendReceiptState = result.onSuccess {
                        setEffect {
                            OnlinePaymentRefundContract.Effect.OnSuccessfullyRefunded
                        }
                    }.toResourceUiState()
                )
            }
        }
    }
}
