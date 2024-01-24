package com.dna.payments.kmm.domain.interactors.helper

import androidx.compose.ui.graphics.Color
import com.dna.payments.kmm.data.model.report.ReportItem
import com.dna.payments.kmm.domain.model.text_switch.DnaOrderByType
import com.dna.payments.kmm.presentation.theme.graph0
import com.dna.payments.kmm.presentation.theme.graph1
import com.dna.payments.kmm.presentation.theme.graph2
import com.dna.payments.kmm.presentation.theme.graph3
import com.dna.payments.kmm.presentation.theme.graph4
import com.dna.payments.kmm.presentation.theme.graph5
import com.dna.payments.kmm.utils.chart.pie.PieChartData

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