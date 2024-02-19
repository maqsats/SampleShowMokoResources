package com.dnapayments.mp.domain.interactors.page_source

import com.dnapayments.mp.data.model.search.Search
import com.dnapayments.mp.domain.model.transactions.Transaction
import com.dnapayments.mp.domain.model.transactions.TransactionPayload
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.TransactionRepository

class OnlinePaymentsPageSource(
    private val transactionRepository: TransactionRepository
) : PageSource<Transaction>() {

    lateinit var search: Search

    override suspend fun onLoadMore(): Response<List<Transaction>> {
        if (noMoreItems) return Response.Success(remoteData)
        currentPage++
        search.paging.page = currentPage
        search.paging.size = TAKE_ITEM_SIZE_IN_ONE_REQUEST

        return when (val response = transactionRepository.getTransactionBySearchParameter(search)) {
            is Response.Success -> handleSuccessResponse(response.data)
            is Response.Error -> handleError(response.error)
            is Response.NetworkError -> handleNetworkError()
            is Response.TokenExpire -> handleNetworkError()
        }
    }

    private fun handleSuccessResponse(data: TransactionPayload): Response<List<Transaction>> {
        remoteData.addAll(data.records ?: emptyList())
        noMoreItems = data.totalCount <= TAKE_ITEM_SIZE_IN_ONE_REQUEST * currentPage
        return Response.Success(remoteData)
    }

    override fun updateParameters(any: Any) {
        search = any as Search
    }

    override fun onReset() {
        remoteData.clear()
        currentPage = FIRST_PAGE
        noMoreItems = false
    }
}