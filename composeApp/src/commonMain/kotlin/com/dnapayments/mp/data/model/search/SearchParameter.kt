package com.dnapayments.mp.data.model.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchParameter(
    val method: String,
    val name: String,
    val searchParameter: List<String>
)