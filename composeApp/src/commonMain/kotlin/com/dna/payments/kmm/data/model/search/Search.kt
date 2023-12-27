package com.dna.payments.kmm.data.model.search

import com.dna.payments.kmm.data.model.search.Paging
import kotlinx.serialization.Serializable

@Serializable
data class Search(
    val orderParameters: List<OrderParameter>,
    val paging: Paging,
    val searchParameters: List<SearchParameter>
)