package com.dna.payments.kmm.presentation.ui.features.team_management

import com.dna.payments.kmm.domain.model.team_management.Teammate
import com.dna.payments.kmm.presentation.model.PagingUiState
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface TeamManagementContract {
    sealed interface Event : UiEvent {
        data object OnInit : Event
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

    sealed interface Effect : UiEffect {
        data class OnPageChanged(
            val position: Int
        ) : Effect
    }
}


