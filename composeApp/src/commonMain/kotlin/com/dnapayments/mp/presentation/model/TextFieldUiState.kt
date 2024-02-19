package com.dnapayments.mp.presentation.model

import androidx.compose.runtime.MutableState
import com.dnapayments.mp.presentation.model.text_input.TextInput
import com.dnapayments.mp.presentation.model.validation_result.ValidationResult

data class TextFieldUiState(
    var input: MutableState<String>,
    val textInput: TextInput,
    var validationResult: MutableState<ValidationResult>,
    val onFieldChanged: () -> Unit = {}
)
