package com.dnapayments.mp.domain.interactors.validation

import com.dnapayments.mp.MR
import com.dnapayments.mp.presentation.model.text_input.TextInput
import com.dnapayments.mp.presentation.model.text_input.TextInput.CARD_NUMBER
import com.dnapayments.mp.presentation.model.text_input.TextInput.CVV_CVC
import com.dnapayments.mp.presentation.model.text_input.TextInput.EXPIRED_DATE
import com.dnapayments.mp.presentation.model.text_input.TextInput.EXPIRE_DATE
import com.dnapayments.mp.presentation.model.validation_result.ValidationResult
import com.dnapayments.mp.utils.UiText
import korlibs.time.DateTime

class ValidateField {

    operator fun invoke(text: String, textInput: TextInput): ValidationResult {
        if (text.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(MR.strings.field_is_required),
                textInput = textInput
            )
        }

        when (textInput) {
            CARD_NUMBER -> {
                if (!isValidCardNumber(text)) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = UiText.StringResource(MR.strings.invalid_card_number),
                        textInput = textInput
                    )
                }
            }

            EXPIRE_DATE -> {
                if (!isValidExpireDate(text)) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = UiText.StringResource(MR.strings.invalid_expire_date),
                        textInput = textInput
                    )
                }
            }

            CVV_CVC -> {
                if (!isValidCVV(text)) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = UiText.StringResource(MR.strings.invalid_cvv),
                        textInput = textInput
                    )
                }
            }

            EXPIRED_DATE -> {
                if (!isValidSelectedDate(text)) {
                    return ValidationResult(
                        successful = false,
                        errorMessage = UiText.StringResource(MR.strings.link_expiration_required),
                        textInput = textInput
                    )
                }
            }

            else -> {
                return ValidationResult(
                    successful = true
                )
            }
        }

        return ValidationResult(successful = true)
    }


    private fun isValidCardNumber(cardNumber: String): Boolean {
        val sanitizedCardNumber = cardNumber.replace("\\s".toRegex(), "")

        if (sanitizedCardNumber.length != 16) {
            return false
        }

        if (!sanitizedCardNumber.matches(Regex("\\d{16}"))) {
            return false
        }

        // Luhn algorithm to validate the card number
        var sum = 0
        val reversedCardNumber = sanitizedCardNumber.reversed()

        for ((index, digitChar) in reversedCardNumber.withIndex()) {
            var digit = digitChar.toString().toInt()

            if (index % 2 == 1) {
                digit *= 2
                if (digit > 9) {
                    digit -= 9
                }
            }

            sum += digit
        }

        return sum % 10 == 0
    }


    private fun isValidExpireDate(expireDate: String): Boolean {
        val dateParts = expireDate.split("/")

        if (dateParts.size != 2) {
            return false
        }

        val (monthStr, yearStr) = dateParts
        val currentYear = DateTime.now().yearInt % 100

        val month = monthStr.toIntOrNull()
        val year = yearStr.toIntOrNull()

        if (month == null || year == null) {
            return false
        }

        if (month < 1 || month > 12) {
            return false
        }

        // Assuming a 20-year window for expiration
        return !(year < currentYear || year > currentYear + 20)
    }

    private fun isValidSelectedDate(date: String?): Boolean {
        return !date.isNullOrEmpty()
    }

    private fun isValidCVV(cvv: String): Boolean {
        return cvv.length in 3..4 && cvv.matches(Regex("\\d+"))
    }
}