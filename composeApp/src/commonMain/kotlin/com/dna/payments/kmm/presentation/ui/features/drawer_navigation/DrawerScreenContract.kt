package com.dna.payments.kmm.presentation.ui.features.drawer_navigation

import com.dna.payments.kmm.data.model.profile.Merchant
import com.dna.payments.kmm.domain.model.nav_item.NavItem
import com.dna.payments.kmm.domain.model.nav_item.SettingsItem
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState
import com.dna.payments.kmm.presentation.ui.common.MerchantName

interface DrawerScreenContract {
    sealed interface Event : UiEvent {
        data class OnMerchantChange(val data: Merchant) : Event
    }

    data class State(
        val navItems: List<NavItem>,
        val settingsItems: List<SettingsItem>,
        val merchants: ResourceUiState<List<Merchant>>,
        val merchantChange: ResourceUiState<Unit>
    ) : UiState

    sealed interface Effect : UiEffect {
        data object OnMerchantChange : Effect
        data class OnMerchantSelected(val merchantName: MerchantName) : Effect
    }
}


