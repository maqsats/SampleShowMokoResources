package com.dna.payments.kmm.domain.interactors.validation

import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.utils.UiText

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