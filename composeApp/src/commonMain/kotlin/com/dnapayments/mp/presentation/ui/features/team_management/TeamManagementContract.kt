package com.dnapayments.mp.presentation.ui.features.team_management

import com.dnapayments.mp.domain.model.team_management.Teammate
import com.dnapayments.mp.presentation.model.PagingUiState
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

interface TeamManagementContract {
    sealed interface Event : UiEvent {
        data class OnPageChanged(
            val position: Int
        ) : Event

        data object OnLoadMore : Event
        data object OnRefresh : Event

        data class OnRoleChange(val selectedRoleIndex: Int) : Event

    }

    data class State(
        val teammateListAll: List<Teammate>,
        val teammateListInvited: List<Teammate>,
        val pagingUiState: PagingUiState,
        val pagingUiStateInvited: PagingUiState,
        val hasPermission: Boolean,
        val selectedPage: Int = 0,
        val roleList: ResourceUiState<List<String>>,
        val indexOfSelectedRole: Int = 0
    ) : UiState

    sealed interface Effect : UiEffect
}


