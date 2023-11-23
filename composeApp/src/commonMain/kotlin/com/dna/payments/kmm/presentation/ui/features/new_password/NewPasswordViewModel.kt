package com.dna.payments.kmm.presentation.ui.features.new_password

import androidx.compose.runtime.mutableStateOf
import com.dna.payments.kmm.domain.interactors.validation.ValidateEmail
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.presentation.mvi.BaseViewModel

class NewPasswordViewModel(
    private val validateEmail: ValidateEmail
) : BaseViewModel<NewPasswordContract.Event, NewPasswordContract.State, NewPasswordContract.Effect>() {

    override fun createInitialState(): NewPasswordContract.State =
        NewPasswordContract.State(
            firstPassword = TextFieldUiState(
                input = mutableStateOf(""),
                textInput = TextInput.EMAIL_ADDRESS,
                validationResult = mutableStateOf(ValidationResult(successful = true)),
                onFieldChanged = { this.setEvent(NewPasswordContract.Event.OnPasswordFieldChanged) }
            ),
            secondPassword = TextFieldUiState(
                input = mutableStateOf(""),
                textInput = TextInput.EMAIL_ADDRESS,
                validationResult = mutableStateOf(ValidationResult(successful = true)),
                onFieldChanged = { this.setEvent(NewPasswordContract.Event.OnPasswordFieldChanged) }
            ),
            isButtonEnabled = mutableStateOf(false),
            resetPassword = ResourceUiState.Idle
        )

    override fun handleEvent(event: NewPasswordContract.Event) {
        when (event) {
            NewPasswordContract.Event.OnButtonClicked -> {

            }

            NewPasswordContract.Event.OnPasswordFieldChanged -> {

            }
        }
    }
}
