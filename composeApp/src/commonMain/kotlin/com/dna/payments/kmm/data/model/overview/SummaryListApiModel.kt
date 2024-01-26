package com.dna.payments.kmm.data.model.overview

import kotlinx.serialization.Serializable

@Serializable
data class SummaryListApiModel(
    val all: List<SummaryPosApiModel>,
    val failed: List<SummaryPosApiModel>,
    val successful: List<SummaryPosApiModel>,
)