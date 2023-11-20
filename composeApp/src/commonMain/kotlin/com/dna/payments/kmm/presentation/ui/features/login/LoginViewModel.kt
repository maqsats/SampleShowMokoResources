package com.dna.payments.kmm.presentation.ui.features.login

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.coroutineScope
import com.dna.payments.kmm.domain.interactors.use_cases.authorization.AuthorizationUseCase
import com.dna.payments.kmm.domain.interactors.validation.ValidateEmail
import com.dna.payments.kmm.domain.interactors.validation.ValidatePassword
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import com.dna.payments.kmm.utils.UiText
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authorizationUseCase: AuthorizationUseCase,
    private val validateEmail: ValidateEmail,
    private val validatePassword: ValidatePassword,
) : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    override fun createInitialState(): LoginContract.State =
        LoginContract.State(
            email = TextFieldUiState(
                input = mutableStateOf(""),
                textInput = TextInput.EMAIL_ADDRESS,
                validationResult = mutableStateOf(ValidationResult(successful = true)),
                onFieldChanged = { this.setEvent(LoginContract.Event.OnEmailFieldChanged) }
            ),
            password = TextFieldUiState(
                input = mutableStateOf(""),
                textInput = TextInput.PASSWORD,
                validationResult = mutableStateOf(ValidationResult(successful = true)),
                onFieldChanged = { this.setEvent(LoginContract.Event.OnPasswordFieldChanged) }
            ),
            authorization = ResourceUiState.Idle,
            isLoginEnabled = mutableStateOf(false)
        )

    override fun handleEvent(event: LoginContract.Event) {
        when (event) {
            LoginContract.Event.OnLoginClicked -> {
                with(currentState) {
                    authorize(email.input.value, password.input.value)
                }
            }

            LoginContract.Event.OnEmailFieldChanged -> {
                with(currentState) {
                    val validateEmailResult = validateEmail(email.input.value, email.textInput)
                    val validatePasswordResult = validatePassword(
                        password.input.value,
                        password.textInput
                    )
                    email.validationResult.value = validateEmailResult
                    isLoginEnabled.value =
                        validateEmailResult.successful && validatePasswordResult.successful
                }
            }

            LoginContract.Event.OnPasswordFieldChanged -> {
                with(currentState) {
                    val validateEmailResult = validateEmail(email.input.value, email.textInput)
                    val validatePasswordResult = validatePassword(
                        password.input.value,
                        password.textInput
                    )
                    password.validationResult.value = validatePasswordResult
                    isLoginEnabled.value =
                        validateEmailResult.successful && validatePasswordResult.successful
                }

            }
        }
    }

    private fun authorize(email: String, password: String) {
        setState { copy(authorization = ResourceUiState.Loading) }
        coroutineScope.launch {
            coroutineScope.launch {
                val result = authorizationUseCase(
                    userName = email,
                    password = password
                )
                setState {
                    copy(
                        authorization = when (result) {
                            is Response.Success -> {
                                ResourceUiState.Success(result.data)
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
}
