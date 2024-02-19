package com.dnapayments.mp.domain.interactors.helper

import androidx.compose.ui.graphics.Color
import com.dnapayments.mp.data.model.report.ReportItem
import com.dnapayments.mp.domain.model.text_switch.DnaOrderByType
import com.dnapayments.mp.presentation.theme.graph0
import com.dnapayments.mp.presentation.theme.graph1
import com.dnapayments.mp.presentation.theme.graph2
import com.dnapayments.mp.presentation.theme.graph3
import com.dnapayments.mp.presentation.theme.graph4
import com.dnapayments.mp.presentation.theme.graph5
import com.dnapayments.mp.utils.chart.pie.PieChartData

object PieChartHelper {

    fun convertResponse(from: List<ReportItem>, orderBy: DnaOrderByType): List<PieChartData> {
        val colors = listOf(graph0, graph1, graph2, graph3, graph4, graph5)

        return from.mapIndexed { index, reportItem ->
            reportItemToPieChartData(reportItem, colors[index % colors.size], orderBy)
        }
    }

    private fun reportItemToPieChartData(
        reportItem: ReportItem,
        color: Color,
        orderBy: DnaOrderByType
    ): PieChartData {
        return PieChartData(
            name = reportItem.value,
            value = when (orderBy) {
                DnaOrderByType.AMOUNT -> reportItem.amount
                else -> reportItem.count.toDouble()
            },
            color = color
        )
    }
}