package com.dnapayments.mp.presentation.ui.features.online_payments.receipt

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.screenModelScope
import com.dnapayments.mp.data.model.payment_methods.SendReceiptRequest
import com.dnapayments.mp.domain.interactors.use_cases.payment_method.SendReceiptOperationUseCase
import com.dnapayments.mp.domain.interactors.validation.ValidateEmail
import com.dnapayments.mp.domain.network.Response.Companion.onSuccess
import com.dnapayments.mp.domain.network.toResourceUiState
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.model.TextFieldUiState
import com.dnapayments.mp.presentation.model.text_input.TextInput
import com.dnapayments.mp.presentation.model.validation_result.ValidationResult
import com.dnapayments.mp.presentation.mvi.BaseViewModel
import kotlinx.coroutines.launch

class GetReceiptViewModel(
    private val validateEmail: ValidateEmail,
    private val sendReceiptOperationUseCase: SendReceiptOperationUseCase
) : BaseViewModel<GetReceiptContract.Event, GetReceiptContract.State, GetReceiptContract.Effect>() {

    override fun createInitialState(): GetReceiptContract.State =
        GetReceiptContract.State(
            email = TextFieldUiState(
                input = mutableStateOf(""),
                textInput = TextInput.EMAIL_ADDRESS,
                validationResult = mutableStateOf(ValidationResult(successful = true)),
                onFieldChanged = { this.setEvent(GetReceiptContract.Event.OnEmailFieldChanged) }
            ),
            isButtonEnabled = mutableStateOf(false),
            sendReceiptState = ResourceUiState.Idle
        )


    override fun handleEvent(event: GetReceiptContract.Event) {
        when (event) {
            GetReceiptContract.Event.OnEmailFieldChanged -> {
                with(currentState) {
                    val validateEmailResult = validateEmail(email.input.value, email.textInput)
                    email.validationResult.value = validateEmailResult
                    isButtonEnabled.value =
                        validateEmailResult.successful
                }
            }

            GetReceiptContract.Event.OnDownloadClicked -> {
            }

            is GetReceiptContract.Event.OnSendClicked -> {
                sendReceipt(event.transactionId)
            }
        }
    }

    private fun sendReceipt(transactionId: String) {
        setState { copy(sendReceiptState = ResourceUiState.Loading) }
        screenModelScope.launch {
            val result = sendReceiptOperationUseCase(
                transactionId = transactionId,
                sendReceiptRequest = SendReceiptRequest(
                    email = currentState.email.input.value
                )
            )
            setState {
                copy(
                    sendReceiptState = result.onSuccess {
                        setEffect {
                            GetReceiptContract.Effect.OnSendSuccess
                        }
                    }.toResourceUiState()
                )
            }
        }
    }
}
