package com.dna.payments.kmm.presentation.ui.features.drawer_navigation

import androidx.compose.runtime.MutableState
import com.dna.payments.kmm.data.model.profile.Merchant
import com.dna.payments.kmm.domain.model.nav_item.NavItem
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface DrawerViewContract {
    sealed interface Event : UiEvent {
        data class OnMerchantChange(val data: Merchant) : Event
    }

    data class State(
        val navItems: MutableState<List<NavItem>>,
        val merchants: ResourceUiState<List<Merchant>>,
        val merchantChange: ResourceUiState<Unit>
    ) : UiState

    sealed interface Effect : UiEffect {
        data object OnMerchantChange : Effect
    }
}


