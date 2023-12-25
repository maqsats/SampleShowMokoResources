package com.dna.payments.kmm.data.model.report

data class ReportApiModel(
    val all: List<ReportItem>,
    val failedList: List<ReportItem>,
    val successfulList: List<ReportItem>
)