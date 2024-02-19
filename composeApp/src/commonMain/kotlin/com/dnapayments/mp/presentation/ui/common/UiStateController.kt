package com.dnapayments.mp.presentation.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.utils.UiText

@Composable
fun UiStateController(
    resourceUiState: ResourceUiState<*>, showSuccessPopup: Boolean = false
) {
    //LoadingPopup
    val loadingPopup = remember { mutableStateOf(false) }
    ProgressLoader(loadingPopup)

    //ErrorPopup
    val uiError = remember { mutableStateOf<UiText>(UiText.DynamicString("")) }
    val uiSuccess = remember { mutableStateOf<UiText>(UiText.DynamicString("")) }
    val errorPopup = remember { mutableStateOf(false) }
    val successPopup = remember { mutableStateOf(false) }
    ErrorPopup(
        showPopup = errorPopup,
        error = uiError.value,
        onSupportScreenClick = {

        }
    )

    SuccessPopup(
        showPopup = successPopup,
        successMessage = uiSuccess.value
    )

    when (resourceUiState) {
        is ResourceUiState.Success -> {
            if (showSuccessPopup) {
                successPopup.value = true
                uiSuccess.value = resourceUiState.message
            }
            errorPopup.value = false
            loadingPopup.value = false
        }

        is ResourceUiState.Empty,
        is ResourceUiState.Idle -> {
            errorPopup.value = false
            loadingPopup.value = false
        }

        is ResourceUiState.Error -> {
            errorPopup.value = true
            uiError.value = resourceUiState.error
            loadingPopup.value = false
        }
        is ResourceUiState.Loading -> {
            loadingPopup.value = true
            errorPopup.value = false
        }
        ResourceUiState.NetworkError -> {
            errorPopup.value = true
            uiError.value = UiText.DynamicString("Network error")
            loadingPopup.value = false
        }
        ResourceUiState.TokenExpire -> {
            errorPopup.value = true
            uiError.value = UiText.DynamicString("Token expired")
            loadingPopup.value = false
        }
    }
}