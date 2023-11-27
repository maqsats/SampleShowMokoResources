package com.dna.payments.kmm.presentation.ui.features.drawer_navigation

import androidx.compose.runtime.MutableState
import com.dna.payments.kmm.domain.model.nav_item.NavItem
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface DrawerNavigationContract {
    sealed interface Event : UiEvent

    data class State(
        val navItems: MutableState<List<NavItem>>,
    ) : UiState

    sealed interface Effect : UiEffect
}


