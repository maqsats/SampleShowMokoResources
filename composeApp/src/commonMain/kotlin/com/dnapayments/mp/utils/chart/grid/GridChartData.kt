package com.dnapayments.mp.utils.chart.grid

import com.dnapayments.mp.utils.chart.line.LegendItemData

interface GridChartData {
    val minX: Long
    val maxX: Long
    val minY: Float
    val maxY: Float
    val legendData: List<LegendItemData>
}
