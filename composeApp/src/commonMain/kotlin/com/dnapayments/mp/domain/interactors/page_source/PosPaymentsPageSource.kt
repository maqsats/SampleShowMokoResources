package com.dnapayments.mp.domain.interactors.page_source

import com.dnapayments.mp.data.model.transactions.pos.PosRequestParam
import com.dnapayments.mp.domain.interactors.use_cases.pos_payments.GetPosTransactionsUseCase
import com.dnapayments.mp.domain.model.transactions.pos.PosTransaction
import com.dnapayments.mp.domain.network.Response

class PosPaymentsPageSource(
    private val getPosTransactionsUseCase: GetPosTransactionsUseCase,
) : PageSource<PosTransaction>() {

    private lateinit var search: PosRequestParam

    override suspend fun onLoadMore(): Response<List<PosTransaction>> {
        if (noMoreItems) return Response.Success(remoteData)
        currentPage++
        search.page = currentPage
        search.size = TAKE_ITEM_SIZE_IN_ONE_REQUEST

        return when (val response = getPosTransactionsUseCase(search)) {
            is Response.Success -> handleSuccessResponse(response.data.first, response.data.second)
            is Response.Error -> handleError(response.error)
            is Response.NetworkError -> handleNetworkError()
            is Response.TokenExpire -> handleNetworkError()
        }
    }

    private fun handleSuccessResponse(
        data: List<PosTransaction>,
        totalCount: Int
    ): Response<List<PosTransaction>> {
        remoteData.addAll(data)
        noMoreItems = totalCount <= TAKE_ITEM_SIZE_IN_ONE_REQUEST * currentPage
        return Response.Success(remoteData)
    }

    override fun updateParameters(any: Any) {
        search = any as PosRequestParam
    }

    override fun onReset() {
        remoteData.clear()
        currentPage = FIRST_PAGE
        noMoreItems = false
    }
}