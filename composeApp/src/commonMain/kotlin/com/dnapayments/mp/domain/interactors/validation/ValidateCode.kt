package com.dnapayments.mp.domain.interactors.validation

import com.dnapayments.mp.MR
import com.dnapayments.mp.presentation.model.text_input.TextInput
import com.dnapayments.mp.presentation.model.validation_result.ValidationResult
import com.dnapayments.mp.utils.UiText

class ValidateCode {

    operator fun invoke(
        verificationCode: String,
        verificationCodeInput: TextInput
    ): ValidationResult {
        if (verificationCode.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(MR.strings.verification_is_blank),
                textInput = verificationCodeInput
            )
        }
        if (verificationCode.length < 6) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(MR.strings.verification_is_invalid),
                textInput = verificationCodeInput
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}