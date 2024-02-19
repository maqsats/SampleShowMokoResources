package com.dnapayments.mp.presentation.ui.features.nav_auth

import androidx.compose.runtime.MutableState
import com.dnapayments.mp.domain.model.nav_auth.NavAuth
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

interface NavAuthContract {
    sealed interface Event : UiEvent

    data class State(
        val navAuth: MutableState<NavAuth>,
    ) : UiState

    sealed interface Effect : UiEffect
}


