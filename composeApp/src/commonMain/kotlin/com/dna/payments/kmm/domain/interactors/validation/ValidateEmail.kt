package com.dna.payments.kmm.domain.interactors.validation

import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.utils.UiText

class ValidateEmail {

    operator fun invoke(email: String, emailAddress: TextInput): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(MR.strings.email_is_blank),
                textInput = emailAddress
            )
        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            return ValidationResult(
//                successful = false,
//                errorMessage = UiText.StringResource(MR.strings.invalid_email),
//                textInput = emailAddress
//            )
//        }
        return ValidationResult(
            successful = true
        )
    }
}