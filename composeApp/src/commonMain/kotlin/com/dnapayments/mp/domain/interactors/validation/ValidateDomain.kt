package com.dnapayments.mp.domain.interactors.validation

import com.dnapayments.mp.MR
import com.dnapayments.mp.presentation.model.text_input.TextInput
import com.dnapayments.mp.presentation.model.validation_result.ValidationResult
import com.dnapayments.mp.utils.UiText

class ValidateDomain {

    operator fun invoke(email: String, emailAddress: TextInput): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(MR.strings.domain_is_required),
                textInput = emailAddress
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}