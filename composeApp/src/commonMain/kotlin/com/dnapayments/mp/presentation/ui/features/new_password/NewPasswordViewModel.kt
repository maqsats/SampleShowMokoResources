package com.dnapayments.mp.presentation.ui.features.new_password

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.screenModelScope
import com.dnapayments.mp.domain.interactors.validation.ValidatePassword
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.use_case.ChangePasswordUseCase
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.model.TextFieldUiState
import com.dnapayments.mp.presentation.model.text_input.TextInput
import com.dnapayments.mp.presentation.model.validation_result.ValidationResult
import com.dnapayments.mp.presentation.mvi.BaseViewModel
import com.dnapayments.mp.utils.UiText
import kotlinx.coroutines.launch

class NewPasswordViewModel(
    private val validatePassword: ValidatePassword,
    private val changePasswordUseCase: ChangePasswordUseCase
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
            is NewPasswordContract.Event.OnButtonClicked -> {
                changePassword(
                    email = event.email,
                    verificationId = event.id,
                    password = currentState.password.input.value
                )
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

    private fun changePassword(email: String, password: String, verificationId: String) {
        setState { copy(newPassword = ResourceUiState.Loading) }
            screenModelScope.launch {
                val result = changePasswordUseCase(
                    email = email,
                    password = password,
                    verificationId = verificationId
                )
                setState {
                    copy(
                        newPassword = when (result) {
                            is Response.Success -> {
                                setEffect {
                                    NewPasswordContract.Effect.OnResetPasswordSuccess
                                }
                                ResourceUiState.Success(
                                    result.data
                                )
                            }

                            is Response.Error -> {
                                ResourceUiState.Error(result.error)
                            }

                            is Response.NetworkError -> {
                                ResourceUiState.Error(UiText.DynamicString("Network error"))
                            }

                            is Response.TokenExpire -> {
                                ResourceUiState.Error(UiText.DynamicString("Token expired"))
                            }
                        }
                    )
                }
        }
    }
}
