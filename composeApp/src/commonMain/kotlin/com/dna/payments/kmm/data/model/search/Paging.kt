package com.dna.payments.kmm.data.model.search

import kotlinx.serialization.Serializable

@Serializable
data class Paging(
    var page: Int,
    var size: Int
)