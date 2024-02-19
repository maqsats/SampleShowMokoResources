package com.dnapayments.mp.presentation.ui.features.restore_password

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.screenModelScope
import com.dnapayments.mp.domain.interactors.validation.ValidateEmail
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.use_case.SendOtpInstructionsUseCase
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.model.TextFieldUiState
import com.dnapayments.mp.presentation.model.text_input.TextInput
import com.dnapayments.mp.presentation.model.validation_result.ValidationResult
import com.dnapayments.mp.presentation.mvi.BaseViewModel
import com.dnapayments.mp.utils.UiText
import kotlinx.coroutines.launch

class RestorePasswordViewModel(
    private val validateEmail: ValidateEmail,
    private val sendOtpInstructionsUseCase: SendOtpInstructionsUseCase
) : BaseViewModel<RestorePasswordContract.Event, RestorePasswordContract.State, RestorePasswordContract.Effect>() {

    override fun createInitialState(): RestorePasswordContract.State =
        RestorePasswordContract.State(
            email = TextFieldUiState(
                input = mutableStateOf(""),
                textInput = TextInput.EMAIL_ADDRESS,
                validationResult = mutableStateOf(ValidationResult(successful = true)),
                onFieldChanged = { this.setEvent(RestorePasswordContract.Event.OnEmailFieldChanged) }
            ),
            isButtonEnabled = mutableStateOf(false),
            sendInstruction = ResourceUiState.Idle
        )

    override fun handleEvent(event: RestorePasswordContract.Event) {
        when (event) {
            RestorePasswordContract.Event.OnButtonClicked -> {
                with(currentState) {
                    sendInstructions(email.input.value)
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

    private fun sendInstructions(email: String) {
        setState { copy(sendInstruction = ResourceUiState.Loading) }
            screenModelScope.launch {
                val result = sendOtpInstructionsUseCase(
                    email = email,
                )
                setState {
                    copy(
                        sendInstruction = when (result) {
                            is Response.Success -> {
                                setEffect {
                                    RestorePasswordContract.Effect.OnInstructionSuccess
                                }
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
