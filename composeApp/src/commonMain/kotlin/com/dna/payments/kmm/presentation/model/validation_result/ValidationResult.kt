package com.dna.payments.kmm.presentation.model.validation_result

import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.utils.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null,
    val textInput: TextInput? = null,
) {
    override fun toString(): String {
        return "ValidationResult(successful=$successful, errorMessage=$errorMessage, textInput=$textInput)"
    }
}