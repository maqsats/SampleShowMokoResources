package com.dnapayments.mp.presentation.model.validation_result

import com.dnapayments.mp.presentation.model.text_input.TextInput
import com.dnapayments.mp.utils.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null,
    val textInput: TextInput? = null,
) {
    override fun toString(): String {
        return "ValidationResult(successful=$successful, errorMessage=$errorMessage, textInput=$textInput)"
    }
}