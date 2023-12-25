package com.dna.payments.kmm.presentation.ui.features.team_management

import com.dna.payments.kmm.domain.model.team_management.TeamManagement
import com.dna.payments.kmm.domain.model.team_management.TeamManagementSearchParameters
import com.dna.payments.kmm.domain.model.team_management.Teammate
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.TeamManagementRepository
import com.dna.payments.kmm.domain.use_case.pagination.PageSource

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