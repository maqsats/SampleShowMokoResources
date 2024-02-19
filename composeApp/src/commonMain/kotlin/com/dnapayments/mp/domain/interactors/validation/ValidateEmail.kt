package com.dnapayments.mp.domain.interactors.validation

import com.dnapayments.mp.MR
import com.dnapayments.mp.presentation.model.text_input.TextInput
import com.dnapayments.mp.presentation.model.validation_result.ValidationResult
import com.dnapayments.mp.utils.UiText
import com.dnapayments.mp.utils.constants.Constants.emailRegex

class ValidateEmail {

    operator fun invoke(email: String, emailAddress: TextInput): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(MR.strings.email_is_blank),
                textInput = emailAddress
            )
        }
        if (!email.matches(emailRegex.toRegex())) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(MR.strings.invalid_email),
                textInput = emailAddress
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}