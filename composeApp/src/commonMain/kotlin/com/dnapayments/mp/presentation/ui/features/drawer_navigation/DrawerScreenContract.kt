package com.dnapayments.mp.presentation.ui.features.drawer_navigation

import com.dnapayments.mp.data.model.profile.Merchant
import com.dnapayments.mp.domain.model.nav_item.NavItem
import com.dnapayments.mp.domain.model.nav_item.SettingsItem
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState
import com.dnapayments.mp.presentation.ui.common.MerchantName

interface DrawerScreenContract {
    sealed interface Event : UiEvent {
        data class OnMerchantChange(val data: Merchant) : Event
        data object OnStart : Event
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


