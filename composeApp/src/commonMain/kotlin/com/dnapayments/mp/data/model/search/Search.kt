package com.dnapayments.mp.data.model.search

import kotlinx.serialization.Serializable

@Serializable
data class Search(
    val orderParameters: List<OrderParameter>,
    val paging: Paging,
    val searchParameters: List<SearchParameter>
)