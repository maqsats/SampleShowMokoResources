package com.dnapayments.mp.presentation.model

import com.dnapayments.mp.utils.UiText


sealed class PagingUiState {
    data class Error(val error: UiText) : PagingUiState()
    data object TokenExpire : PagingUiState()
    data object Idle : PagingUiState()
    data object NetworkError : PagingUiState()
    data object Loading : PagingUiState()
}
