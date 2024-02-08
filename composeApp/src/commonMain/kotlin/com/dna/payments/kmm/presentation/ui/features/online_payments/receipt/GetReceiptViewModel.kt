package com.dna.payments.kmm.presentation.ui.features.online_payments.receipt

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.screenModelScope
import com.dna.payments.kmm.data.model.payment_methods.SendReceiptRequest
import com.dna.payments.kmm.domain.interactors.use_cases.payment_method.SendReceiptOperationUseCase
import com.dna.payments.kmm.domain.interactors.validation.ValidateEmail
import com.dna.payments.kmm.domain.network.Response.Companion.onSuccess
import com.dna.payments.kmm.domain.network.toResourceUiState
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
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
