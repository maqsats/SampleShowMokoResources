package com.dna.payments.kmm.data.model.search

data class SearchParameter(
    val method: String,
    val name: String,
    val searchParameter: List<String>
)