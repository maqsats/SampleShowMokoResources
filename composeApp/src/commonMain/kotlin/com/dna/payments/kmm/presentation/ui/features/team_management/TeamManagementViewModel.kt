package com.dna.payments.kmm.presentation.ui.features.team_management

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.screenModelScope
import com.dna.payments.kmm.domain.interactors.use_cases.access_level.AccessLevelUseCase
import com.dna.payments.kmm.domain.model.permissions.AccessLevel
import com.dna.payments.kmm.domain.model.permissions.Section
import com.dna.payments.kmm.domain.model.team_management.TeamManagementSearchParameters
import com.dna.payments.kmm.domain.model.team_management.UserType
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.presentation.model.PagingUiState
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.launch

class TeamManagementViewModel(
    private val teamManagementByUserPageSource: TeamManagementByUserPageSource,
    private val teamManagementInvitedPageSource: TeamManagementInvitedPageSource,
    private val accessLevelUseCase: AccessLevelUseCase
) : BaseViewModel<TeamManagementContract.Event, TeamManagementContract.State, TeamManagementContract.Effect>() {

    private var role: UserType = UserType.ALL

    init {
        setState {
            copy(
                hasPermission =
                accessLevelUseCase.hasPermission(
                    Section.TEAM_MANAGEMENT,
                    AccessLevel.FULL
                ),
                roleList = ResourceUiState.Success(UserType.entries.map { it.displayName })
            )
        }
        teamManagementByUserPageSource.onReset()
        teamManagementInvitedPageSource.onReset()
        getTeammateList()
        getInvitedTeammateList()
    }

    override fun createInitialState(): TeamManagementContract.State =
        TeamManagementContract.State(
            teammateListAll = mutableStateListOf(),
            teammateListInvited = mutableStateListOf(),
            hasPermission = false,
            selectedPage = 0,
            roleList = ResourceUiState.Idle,
            pagingUiState = PagingUiState.Loading,
            pagingUiStateInvited = PagingUiState.Loading
        )

    override fun handleEvent(event: TeamManagementContract.Event) {
        when (event) {
            is TeamManagementContract.Event.OnPageChanged -> {
                println("TeamManagementViewModel: handleEvent: OnPageChanged: event.position: ${event.position}")
                setState {
                    copy(selectedPage = event.position)
                }
            }

            is TeamManagementContract.Event.OnRoleChange -> {
                setState {
                    copy(indexOfSelectedRole = event.selectedRoleIndex)
                }
                role = UserType.entries.toTypedArray()[event.selectedRoleIndex]
                teamManagementByUserPageSource.onReset()
                teamManagementInvitedPageSource.onReset()
                getTeammateList()
                getInvitedTeammateList()
            }

            TeamManagementContract.Event.OnLoadMore -> {
                if (!teamManagementByUserPageSource.getIsLastPage()) {
                    getTeammateList()
                }
                if (!teamManagementInvitedPageSource.getIsLastPage()) {
                    getInvitedTeammateList()
                }
            }

            TeamManagementContract.Event.OnRefresh -> {
                teamManagementByUserPageSource.onReset()
                teamManagementInvitedPageSource.onReset()
                getTeammateList()
                getInvitedTeammateList()
            }
        }
    }

    private fun getInvitedTeammateList() {
        screenModelScope.launch {
            setState {
                copy(
                    pagingUiStateInvited = PagingUiState.Loading
                )
            }
            teamManagementInvitedPageSource.updateParameters(
                TeamManagementSearchParameters(
                    role = if (role == UserType.ALL) "" else role.displayName.lowercase(),
                    isActive = false
                )
            )
            val result = teamManagementInvitedPageSource.onLoadMore()
            setState {
                copy(
                    pagingUiStateInvited = when (result) {
                        is Response.Success -> {
                            PagingUiState.Idle
                        }

                        is Response.Error -> {
                            PagingUiState.Error(result.error)
                        }

                        is Response.NetworkError -> {
                            PagingUiState.NetworkError
                        }

                        is Response.TokenExpire -> {
                            PagingUiState.TokenExpire
                        }
                    },
                    teammateListInvited = teamManagementInvitedPageSource.remoteData
                )
            }
        }
    }

    private fun getTeammateList() {
        screenModelScope.launch {
            setState {
                copy(
                    pagingUiState = PagingUiState.Loading
                )
            }
            teamManagementByUserPageSource.updateParameters(
                TeamManagementSearchParameters(
                    role = if (role == UserType.ALL) "" else role.displayName.lowercase(),
                    isActive = true
                )
            )
            val result = teamManagementByUserPageSource.onLoadMore()
            setState {
                copy(
                    pagingUiState = when (result) {
                        is Response.Success -> {
                            PagingUiState.Idle
                        }

                        is Response.Error -> {
                            PagingUiState.Error(result.error)
                        }

                        is Response.NetworkError -> {
                            PagingUiState.NetworkError
                        }

                        is Response.TokenExpire -> {
                            PagingUiState.TokenExpire
                        }
                    },
                    teammateListAll = teamManagementByUserPageSource.remoteData
                )
            }
        }
    }
}
