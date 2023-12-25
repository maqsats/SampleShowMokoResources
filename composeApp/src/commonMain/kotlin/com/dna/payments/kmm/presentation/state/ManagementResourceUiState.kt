package com.dna.payments.kmm.presentation.state

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dna.payments.kmm.presentation.model.ResourceUiState

@Composable
fun <T> ManagementResourceUiState(
    modifier: Modifier = Modifier,
    resourceUiState: ResourceUiState<T>,
    successView: @Composable (data: T) -> Unit,
    loadingView: @Composable () -> Unit = { Loading() },
    onTryAgain: () -> Unit,
    msgTryAgain: String = "No data to show",
    onCheckAgain: () -> Unit,
    onTokenExpire: () -> Unit = { },
    msgCheckAgain: @Composable () -> Unit = { Empty() }
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        when (resourceUiState) {
            is ResourceUiState.Empty -> msgCheckAgain()
            is ResourceUiState.Error -> msgCheckAgain()
            ResourceUiState.Loading -> loadingView()
            is ResourceUiState.Success -> successView(resourceUiState.data)
            ResourceUiState.Idle -> Unit
            ResourceUiState.NetworkError -> msgCheckAgain()
            ResourceUiState.TokenExpire -> onTokenExpire()
        }
    }
}