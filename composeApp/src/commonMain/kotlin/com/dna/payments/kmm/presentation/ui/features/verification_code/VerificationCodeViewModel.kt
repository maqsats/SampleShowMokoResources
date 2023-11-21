package com.dna.payments.kmm.presentation.ui.features.verification_code

import androidx.compose.runtime.mutableStateOf
import com.dna.payments.kmm.domain.interactors.validation.ValidateCode
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.presentation.mvi.BaseViewModel

class VerificationCodeViewModel(
    private val validateCode: ValidateCode
) : BaseViewModel<VerificationCodeContract.Event, VerificationCodeContract.State, VerificationCodeContract.Effect>() {

    override fun createInitialState(): VerificationCodeContract.State =
        VerificationCodeContract.State(
            verificationCode = TextFieldUiState(
                input = mutableStateOf(""),
                textInput = TextInput.VERIFICATION_CODE,
                validationResult = mutableStateOf(ValidationResult(successful = true)),
                onFieldChanged = { this.setEvent(VerificationCodeContract.Event.OnEmailFieldChanged) }
            ),
            isButtonEnabled = mutableStateOf(false),
            sendCode = ResourceUiState.Idle
        )

    override fun handleEvent(event: VerificationCodeContract.Event) {
        when (event) {
            VerificationCodeContract.Event.OnButtonClicked -> {
                with(currentState) {
                }
            }

            VerificationCodeContract.Event.OnEmailFieldChanged -> {
                with(currentState) {
                    val verificationCodeResult =
                        validateCode(verificationCode.input.value, verificationCode.textInput)

                    verificationCode.validationResult.value = verificationCodeResult
                    isButtonEnabled.value =
                        verificationCodeResult.successful
                }
            }
        }
    }
}
