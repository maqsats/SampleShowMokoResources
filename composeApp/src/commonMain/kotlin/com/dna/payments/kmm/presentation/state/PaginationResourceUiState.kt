package com.dna.payments.kmm.presentation.state

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.dna.payments.kmm.presentation.model.PagingUiState
import com.dna.payments.kmm.presentation.ui.common.ErrorPopup
import com.dna.payments.kmm.utils.UiText
import com.dna.payments.kmm.utils.compose_paging.items
import com.dna.payments.kmm.utils.pull_to_refresh.DNAPullToRefreshIndicatorBottom
import com.dna.payments.kmm.utils.pull_to_refresh.PullToRefresh
import com.dna.payments.kmm.utils.pull_to_refresh.rememberPullToRefreshState

@Composable
fun <T : Any> PaginationUiStateManager(
    modifier: Modifier = Modifier,
    resourceUiState: PagingUiState,
    pagingList: List<T>,
    successItemView: @Composable (data: T) -> Unit,
    loadingView: @Composable () -> Unit = { Loading() },
    isToolbarCollapsed: Boolean = false,
    emptyView: @Composable () -> Unit = { Empty() },
    onRequestNextPage: () -> Unit = { },
    onRefresh: () -> Unit = { },
) {
    val errorPopup = remember { mutableStateOf(false) }
    val uiError = remember { mutableStateOf<UiText>(UiText.DynamicString("")) }

    ErrorPopup(
        showPopup = errorPopup,
        error = uiError.value,
        onSupportScreenClick = {

        }
    )

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(pagingList) {
        isRefreshing = false
    }

    when (resourceUiState) {
        is PagingUiState.Error -> {
            errorPopup.value = true
            uiError.value = resourceUiState.error
        }
        PagingUiState.NetworkError -> {
            errorPopup.value = true
            uiError.value = UiText.DynamicString("Network Error")
        }
        PagingUiState.TokenExpire -> {

        }
        PagingUiState.Loading -> if (pagingList.isEmpty()) PaginationLoadingView { loadingView() }
        PagingUiState.Idle -> if (pagingList.isEmpty()) emptyView()
    }

    PullToRefresh(
        modifier = modifier,
        state = rememberPullToRefreshState(isRefreshing = isRefreshing),
        onRefresh = onRefresh,
        enabled = !isToolbarCollapsed
    ) {
        LazyColumn(modifier = Modifier) {
            items(
                feed = pagingList,
                requestNextPage = onRequestNextPage
            ) {
                successItemView(it)
            }
            item {
                if (
                    resourceUiState is PagingUiState.Loading &&
                    pagingList.isNotEmpty()
                ) {
                    DNAPullToRefreshIndicatorBottom()
                }
            }
        }
    }
}

@Composable
fun PaginationLoadingView(loadingView: @Composable () -> Unit) {
    LazyColumn {
        items(10) {
            loadingView()
        }
    }
}