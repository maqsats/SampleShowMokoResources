package com.dna.payments.kmm.presentation.ui.features.restore_password

import androidx.compose.runtime.mutableStateOf
import com.dna.payments.kmm.domain.interactors.validation.ValidateEmail
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.presentation.mvi.BaseViewModel

class RestorePasswordViewModel(
    private val validateEmail: ValidateEmail
) : BaseViewModel<RestorePasswordContract.Event, RestorePasswordContract.State, RestorePasswordContract.Effect>() {

    override fun createInitialState(): RestorePasswordContract.State =
        RestorePasswordContract.State(
            email = TextFieldUiState(
                input = mutableStateOf(""),
                textInput = TextInput.EMAIL_ADDRESS,
                validationResult = mutableStateOf(ValidationResult(successful = true)),
                onFieldChanged = { this.setEvent(RestorePasswordContract.Event.OnEmailFieldChanged) }
            ),
            isButtonEnabled = mutableStateOf(false)
        )

    override fun handleEvent(event: RestorePasswordContract.Event) {
        when (event) {
            RestorePasswordContract.Event.OnButtonClicked -> {
                with(currentState) {
                    val validateEmailResult = validateEmail(email.input.value, email.textInput)
                    email.validationResult.value = validateEmailResult

                    if (validateEmailResult.successful) {
                        //TODO
                    }
                }
            }

            RestorePasswordContract.Event.OnEmailFieldChanged -> {
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
