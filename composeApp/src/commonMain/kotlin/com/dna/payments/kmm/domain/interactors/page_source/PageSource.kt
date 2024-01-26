package com.dna.payments.kmm.domain.interactors.page_source

import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.utils.UiText

abstract class PageSource<T> {
    var noMoreItems = false
    var currentPage = FIRST_PAGE
    var remoteData = mutableListOf<T>()

    abstract suspend fun onLoadMore(): Response<List<T>>

    fun getIsLastPage() = noMoreItems

    abstract fun updateParameters(any: Any)

    abstract fun onReset()

    internal fun handleError(error: UiText): Response<List<T>> {
        currentPage--
        noMoreItems = false
        return Response.Error(error)
    }

    internal fun handleNetworkError(): Response<List<T>> {
        if (currentPage > FIRST_PAGE)
            currentPage--
        noMoreItems = false
        return Response.NetworkError
    }

    companion object {
        internal const val FIRST_PAGE = 0
        internal const val TAKE_ITEM_SIZE_IN_ONE_REQUEST = 20
    }
}