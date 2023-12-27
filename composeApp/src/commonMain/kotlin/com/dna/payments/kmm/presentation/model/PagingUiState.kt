package com.dna.payments.kmm.presentation.model

import com.dna.payments.kmm.utils.UiText


sealed class PagingUiState {
    data class Error(val error: UiText) : PagingUiState()
    data object TokenExpire : PagingUiState()
    data object Idle : PagingUiState()
    data object NetworkError : PagingUiState()
    data object Loading : PagingUiState()
}
