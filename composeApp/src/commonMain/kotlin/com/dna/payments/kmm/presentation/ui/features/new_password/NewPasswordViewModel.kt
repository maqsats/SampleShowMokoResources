package com.dna.payments.kmm.presentation.ui.features.new_password

import androidx.compose.runtime.mutableStateOf
import com.dna.payments.kmm.domain.interactors.validation.ValidatePassword
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.presentation.mvi.BaseViewModel

class NewPasswordViewModel(
    private val validatePassword: ValidatePassword
) : BaseViewModel<NewPasswordContract.Event, NewPasswordContract.State, NewPasswordContract.Effect>() {

    override fun createInitialState(): NewPasswordContract.State =
        NewPasswordContract.State(
            password = TextFieldUiState(
                input = mutableStateOf(""),
                textInput = TextInput.PASSWORD,
                validationResult = mutableStateOf(ValidationResult(successful = true)),
                onFieldChanged = {
                    this.setEvent(NewPasswordContract.Event.OnPasswordFieldChanged)
                }
            ),
            confirmPassword = TextFieldUiState(
                input = mutableStateOf(""),
                textInput = TextInput.PASSWORD,
                validationResult = mutableStateOf(ValidationResult(successful = true)),
                onFieldChanged = { this.setEvent(NewPasswordContract.Event.OnConfirmPasswordFieldChanged) }
            ),
            isButtonEnabled = mutableStateOf(false),
            newPassword = ResourceUiState.Idle,
            validationList = mutableStateOf(PasswordRequirementsDataFactory.getRequirementList())
        )

    override fun handleEvent(event: NewPasswordContract.Event) {
        when (event) {
            NewPasswordContract.Event.OnButtonClicked -> {

            }

            NewPasswordContract.Event.OnPasswordFieldChanged -> {
                with(currentState) {
                    validationList.value =
                        PasswordRequirementsDataFactory.getRequirementList(
                            currentState.password.input.value,
                            currentState.confirmPassword.input.value
                        )
                    val validatePasswordResult = validatePassword(
                        password.input.value,
                        password.textInput
                    )
                    password.validationResult.value = validatePasswordResult
                    isButtonEnabled.value = validationList.value.all { it.isValid }
                }
            }

            NewPasswordContract.Event.OnConfirmPasswordFieldChanged -> {
                with(currentState) {
                    validationList.value =
                        PasswordRequirementsDataFactory.getRequirementList(
                            currentState.password.input.value,
                            currentState.confirmPassword.input.value
                        )
                    val validateConfirmPasswordResult = validatePassword(
                        confirmPassword.input.value,
                        password.textInput
                    )
                    confirmPassword.validationResult.value = validateConfirmPasswordResult
                    isButtonEnabled.value = validationList.value.all { it.isValid }
                }
            }
        }
    }
}
