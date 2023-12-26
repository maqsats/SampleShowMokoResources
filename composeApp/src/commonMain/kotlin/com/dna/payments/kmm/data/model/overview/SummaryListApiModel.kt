package com.dna.payments.kmm.data.model.overview

import kotlinx.serialization.Serializable

@Serializable
data class SummaryListApiModel(
    val all: List<SummaryPosApiModel>
)