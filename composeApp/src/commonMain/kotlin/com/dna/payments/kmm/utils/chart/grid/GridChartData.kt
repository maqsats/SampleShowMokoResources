package com.dna.payments.kmm.utils.chart.grid

import com.dna.payments.kmm.utils.chart.line.LegendItemData

interface GridChartData {
    val minX: Long
    val maxX: Long
    val minY: Float
    val maxY: Float
    val legendData: List<LegendItemData>
}
