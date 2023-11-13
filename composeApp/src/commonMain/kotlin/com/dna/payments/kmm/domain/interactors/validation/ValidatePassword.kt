package com.dna.payments.kmm.domain.interactors.validation

import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.utils.UiText

class ValidatePassword {

    operator fun invoke(password: String, textInput: TextInput): ValidationResult =
        when {
            !containsAtLeastEightChars(password) -> {
                ValidationResult(
                    successful = false,
                    errorMessage = UiText.StringResource(MR.strings.at_least_8_length_char),
                    textInput = textInput
                )
            }
            !containsAtLeastOneDigitAndOneLetter(password) -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(MR.strings.at_one_digit_or_letter),
                textInput = textInput
            )
            !containsSpecialChar(password) -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(MR.strings.at_least_1_special_char),
                textInput = textInput
            )
            !containsUpperCase(password) -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(MR.strings.at_least_1_uppercase),
                textInput = textInput
            )
            !containsLowerCase(password) -> ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(MR.strings.at_least_1_lowercase),
                textInput = textInput
            )
            else -> ValidationResult(
                successful = true
            )
        }


    private fun containsAtLeastEightChars(password: String) = password.length >= 8

    private fun containsAtLeastOneDigitAndOneLetter(password: String) =
        password.any { it.isDigit() } && password.any { it.isLetter() }

    private fun containsSpecialChar(password: String) =
        password.matches("^.*[^a-zA-Z\\d\\s][^a-zA-Z]*\$".toRegex())

    private fun containsUpperCase(password: String) = password.any { it.isUpperCase() }

    private fun containsLowerCase(password: String) = password.any { it.isLowerCase() }
}