package com.dnapayments.mp.domain.interactors.validation

import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.online_payments.OnlinePaymentOperationType
import com.dnapayments.mp.presentation.model.text_input.TextInput
import com.dnapayments.mp.presentation.model.validation_result.ValidationResult
import com.dnapayments.mp.utils.UiText

class ValidatePaymentAmount {

    operator fun invoke(
        amount: String,
        balance: Double,
        textInput: TextInput,
        onlinePaymentOperationType: OnlinePaymentOperationType = OnlinePaymentOperationType.CHARGE
    ): ValidationResult {
        if (amount.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResource(MR.strings.amount_required),
                textInput = textInput
            )
        }

        if (amount.toDouble() > balance) {
            val errorMessageRes =
                if (onlinePaymentOperationType == OnlinePaymentOperationType.CHARGE) {
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