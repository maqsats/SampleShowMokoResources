package com.dna.payments.kmm.data.model.report

import kotlinx.serialization.Serializable

@Serializable
data class ReportPosPaymentsApiModel(
    val all: List<ReportItem>,
    val failed: List<ReportItem>,
    val successful: List<ReportItem>
)