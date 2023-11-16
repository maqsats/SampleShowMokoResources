package com.dna.payments.kmm.presentation.ui.features.nav_auth

import androidx.compose.runtime.MutableState
import com.dna.payments.kmm.domain.model.nav_auth.NavAuth
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface NavAuthContract {
    sealed interface Event : UiEvent

    data class State(
        val navAuth: MutableState<NavAuth>,
    ) : UiState

    sealed interface Effect : UiEffect
}


