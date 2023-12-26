package com.dna.payments.kmm.domain.model.reports

data class HistogramEntry(
    val amountList: List<BarEntry>,
    val countList: List<BarEntry>,
    val labelList: List<String>,
)
