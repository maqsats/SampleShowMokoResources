package com.dna.payments.kmm.domain.interactors.validation

import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.online_payments.OperationType
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult
import com.dna.payments.kmm.utils.UiText

class ValidatePaymentAmount {

    operator fun invoke(
        amount: String,
        balance: Int,
        textInput: TextInput,
        operationType: OperationType = OperationType.CHARGE
    ): ValidationResult {
        if (amount.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(MR.strings.amount_required),
                textInput = textInput
            )
        }

        if (amount.toDouble() > balance.toDouble()) {
            val errorMessageRes = if (operationType == OperationType.CHARGE) {
                MR.strings.amount_must_be
            } else {
                MR.strings.refund_amount_must_be
            }

            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(errorMessageRes),
                textInput = textInput
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}