package daniel.avila.rnm.kmm.utils.chart.grid

import daniel.avila.rnm.kmm.utils.chart.line.LegendItemData

interface GridChartData {
    val minX: Long
    val maxX: Long
    val minY: Float
    val maxY: Float
    val legendData: List<LegendItemData>
}
