package com.dna.payments.kmm.presentation.model

import androidx.compose.runtime.MutableState
import com.dna.payments.kmm.presentation.model.text_input.TextInput
import com.dna.payments.kmm.presentation.model.validation_result.ValidationResult

data class TextFieldUiState(
    var input: MutableState<String>,
    val textInput: TextInput,
    var validationResult: MutableState<ValidationResult>,
)
