package com.dna.payments.kmm.presentation.model

import com.dna.payments.kmm.utils.UiText

sealed interface ResourceUiState<out T> {
    data class Success<T>(val data: T, val message: UiText = UiText.DynamicString("")) :
        ResourceUiState<T>

    data class Error(val error: UiText) : ResourceUiState<Nothing>
    data object NetworkError : ResourceUiState<Nothing>
    data object TokenExpire : ResourceUiState<Nothing>
    data object Loading : ResourceUiState<Nothing>
    data object Empty : ResourceUiState<Nothing>
    data object Idle : ResourceUiState<Nothing>
}
