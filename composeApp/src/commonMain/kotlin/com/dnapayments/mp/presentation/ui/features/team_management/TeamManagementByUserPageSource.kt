package com.dnapayments.mp.presentation.ui.features.team_management

import com.dnapayments.mp.domain.model.team_management.TeamManagement
import com.dnapayments.mp.domain.model.team_management.TeamManagementSearchParameters
import com.dnapayments.mp.domain.model.team_management.Teammate
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.TeamManagementRepository
import com.dnapayments.mp.domain.interactors.page_source.PageSource

class TeamManagementByUserPageSource(
    private val teamManagementRepository: TeamManagementRepository
) : PageSource<Teammate>() {

    private lateinit var search: TeamManagementSearchParameters

    override suspend fun onLoadMore(): Response<List<Teammate>> {
        if (noMoreItems) return Response.Success(remoteData)
        currentPage++
        search.page = currentPage
        search.size = TAKE_ITEM_SIZE_IN_ONE_REQUEST

        return when (val response = teamManagementRepository.getTeamManagement(
            isActive = search.isActive,
            role = search.role,
            page = search.page,
            size = search.size
        )) {
            is Response.Success -> handleSuccessResponse(response.data)
            is Response.Error -> handleError(response.error)
            is Response.NetworkError -> handleNetworkError()
            Response.TokenExpire -> handleNetworkError()
        }
    }

    private fun handleSuccessResponse(data: TeamManagement): Response<List<Teammate>> {
        remoteData.addAll(data.teammateList)
        noMoreItems = data.totalCount <= TAKE_ITEM_SIZE_IN_ONE_REQUEST * currentPage
        return Response.Success(remoteData)
    }

    override fun updateParameters(any: Any) {
        search = any as TeamManagementSearchParameters
    }

    override fun onReset() {
        remoteData.clear()
        currentPage = FIRST_PAGE
        noMoreItems = false
    }
}