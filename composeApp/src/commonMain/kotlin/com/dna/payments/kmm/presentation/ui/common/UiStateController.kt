package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.utils.UiText

@Composable
fun UiStateController(
    resourceUiState: ResourceUiState<*>,
    success: () -> Unit = {}
) {
    //LoadingPopup
    val loadingPopup = remember { mutableStateOf(false) }
    ProgressLoader(loadingPopup)

    //ErrorPopup
    val uiError = remember { mutableStateOf<UiText>(UiText.DynamicString("")) }
    val errorPopup = remember { mutableStateOf(false) }
    ErrorPopup(
        showPopup = errorPopup,
        error = uiError.value,
        onSupportScreenClick = {

        }
    )

    when (resourceUiState) {
        is ResourceUiState.Success,
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
    }
}