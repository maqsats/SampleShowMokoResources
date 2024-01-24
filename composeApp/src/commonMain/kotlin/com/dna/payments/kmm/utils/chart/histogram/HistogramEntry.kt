package com.dna.payments.kmm.utils.chart.histogram

data class HistogramEntry(
    val amountList: List<BarEntry>,
    val countList: List<BarEntry>,
    val labelList: List<String>,
)
