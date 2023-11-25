package com.dna.payments.kmm.domain.interactors.validation

import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.utils.UiText

class ValidatePassword {

    operator fun invoke(
        password: String,
        textInput: TextInput
    ): ValidationResult =
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


    fun containsAtLeastEightChars(password: String) = password.length >= 8

    fun containsAtLeastOneDigitAndOneLetter(password: String) =
        password.any { it.isDigit() } && password.any { it.isLetter() }

    fun containsSpecialChar(password: String) =
        password.matches("^.*[^a-zA-Z\\d\\s][^a-zA-Z]*\$".toRegex())

    fun containsUpperCase(password: String) = password.any { it.isUpperCase() }

    fun containsLowerCase(password: String) = password.any { it.isLowerCase() }

    fun passwordsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword && password.isNotEmpty()
    }
}