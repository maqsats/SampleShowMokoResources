package com.dna.payments.kmm.presentation.ui.features.forgot_password

import androidx.compose.runtime.mutableStateOf
import com.dna.payments.kmm.domain.interactors.validation.ValidateEmail
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.presentation.mvi.BaseViewModel

class ForgotPasswordViewModel(
    private val validateEmail: ValidateEmail
) : BaseViewModel<ForgotPasswordContract.Event, ForgotPasswordContract.State, ForgotPasswordContract.Effect>() {

    override fun createInitialState(): ForgotPasswordContract.State =
        ForgotPasswordContract.State(
            email = TextFieldUiState(
                input = mutableStateOf(""),
                textInput = TextInput.EMAIL_ADDRESS,
                validationResult = mutableStateOf(ValidationResult(successful = true)),
                onFieldChanged = { this.setEvent(ForgotPasswordContract.Event.OnEmailFieldChanged) }
            ),
            isButtonEnabled = mutableStateOf(false)
        )

    override fun handleEvent(event: ForgotPasswordContract.Event) {
        when (event) {
            ForgotPasswordContract.Event.OnButtonClicked -> {
                with(currentState) {
                    val validateEmailResult = validateEmail(email.input.value, email.textInput)
                    email.validationResult.value = validateEmailResult

                    if (validateEmailResult.successful) {
                        //TODO
                    }
                }
            }

            ForgotPasswordContract.Event.OnEmailFieldChanged -> {
                with(currentState) {
                    val validateEmailResult = validateEmail(email.input.value, email.textInput)

                    email.validationResult.value = validateEmailResult
                    isButtonEnabled.value =
                        validateEmailResult.successful
                }
            }
        }
    }
}
