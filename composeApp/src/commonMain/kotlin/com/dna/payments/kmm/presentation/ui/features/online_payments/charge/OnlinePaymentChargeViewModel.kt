package com.dna.payments.kmm.presentation.ui.features.online_payments.charge

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.screenModelScope
import com.dna.payments.kmm.domain.interactors.use_cases.online_payments.ChargePaymentOperationUseCase
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

class OnlinePaymentChargeViewModel(
    private val validate: ValidatePaymentAmount,
    private val chargePaymentOperationUseCase: ChargePaymentOperationUseCase
) : BaseViewModel<OnlinePaymentChargeContract.Event, OnlinePaymentChargeContract.State, OnlinePaymentChargeContract.Effect>() {

    override fun createInitialState(): OnlinePaymentChargeContract.State =
        OnlinePaymentChargeContract.State(
            amount = TextFieldUiState(
                input = mutableStateOf(""),
                textInput = TextInput.AMOUNT,
                validationResult = mutableStateOf(ValidationResult(successful = true)),
                onFieldChanged = { this.setEvent(OnlinePaymentChargeContract.Event.OnAmountFieldChanged) }
            ),
            isButtonEnabled = mutableStateOf(true),
            chargeState = ResourceUiState.Idle,
            balance = mutableStateOf(0.0)
        )


    override fun handleEvent(event: OnlinePaymentChargeContract.Event) {
        when (event) {
            OnlinePaymentChargeContract.Event.OnAmountFieldChanged -> {
                with(currentState) {
                    val validateAmountResult = validate(
                        amount = currentState.amount.input.value,
                        balance = balance.value,
                        textInput = currentState.amount.textInput,
                        operationType = OperationType.CHARGE
                    )
                    amount.validationResult.value = validateAmountResult
                    isButtonEnabled.value = validateAmountResult.successful
                }
            }

            is OnlinePaymentChargeContract.Event.OnChargeClicked -> {
                refund(event.transactionId)
            }

            is OnlinePaymentChargeContract.Event.OnInit -> {
                with(currentState) {
                    amount.input.value = event.amount.toInt().toString()
                    balance.value = event.balance
                }
            }
        }
    }

    private fun refund(transactionId: String) {
        setState { copy(chargeState = ResourceUiState.Loading) }
        screenModelScope.launch {
            val result = chargePaymentOperationUseCase(
                transactionId = transactionId,
                amount = currentState.amount.input.value.toInt()
            )
            setState {
                copy(
                    chargeState = result.onSuccess {
                        setEffect {
                            OnlinePaymentChargeContract.Effect.OnSuccessfullyCharge(it.id)
                        }
                    }.toResourceUiState()
                )
            }
        }
    }
}
