package com.dna.payments.kmm.domain.interactors.use_cases.reports

import com.dna.payments.kmm.data.model.overview.SummaryPosApiModel
import com.dna.payments.kmm.data.model.overview.SummaryPosRequest
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants
import com.dna.payments.kmm.domain.model.date_picker.IntervalType
import com.dna.payments.kmm.domain.model.reports.BarEntry
import com.dna.payments.kmm.domain.model.reports.HistogramEntry
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.TransactionRepository
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTimeSpan
import com.soywiz.klock.parse
import com.soywiz.klock.weekOfYear1

class DefaultPosSummaryGraphUseCase(private val transactionRepository: TransactionRepository) :
    PosSummaryGraphUseCase {

    override suspend fun getPosSummaryGraph(
        summaryPosRequest: SummaryPosRequest, intervalType: IntervalType
    ): Response<HistogramEntry> =
        when (val response = transactionRepository.getPosGraphSummary(
            summaryPosRequest
        )) {
            is Response.Error -> Response.Error(response.error)
            is Response.NetworkError -> Response.NetworkError
            is Response.Success -> Response.Success(
                convertToBarEntries(
                    response.data.all,
                    intervalType
                )
            )
            is Response.TokenExpire -> Response.TokenExpire
        }

    private fun convertToBarEntries(
        summaryList: List<SummaryPosApiModel>,
        intervalType: IntervalType
    ): HistogramEntry {
        val sortedList = summaryList.sortedBy { it.value }

        val dateFormat = DateFormat(
            if (intervalType == IntervalType.HOUR) DatePickerConstants.DATE_FORMAT_WITH_HOUR_POS
            else DatePickerConstants.DATE_FORMAT_WITHOUT_HOUR
        )

        var earliestDate = dateFormat.parse(sortedList.first().value)

        val interval = when (intervalType) {
            IntervalType.HOUR -> DateTimeSpan(hours = 1)
            IntervalType.DAY -> DateTimeSpan(days = 1)
            IntervalType.WEEK -> DateTimeSpan(weeks = 1)
            IntervalType.MONTH -> DateTimeSpan(months = 1)
            IntervalType.YEAR -> DateTimeSpan(years = 1)
        }

        val labelList = mutableListOf<String>()

        val amountList = mutableListOf<BarEntry>()
        val countList = mutableListOf<BarEntry>()


        sortedList.mapIndexed { index, summary ->
            val yValue = index.toFloat()

            val label = when (intervalType) {
                IntervalType.HOUR ->
                    earliestDate.format(DatePickerConstants.TIME_FORMAT_HOUR_MIN)
                IntervalType.DAY ->
                    earliestDate.format(DatePickerConstants.TIME_FORMAT_DAY_MONTH)
                IntervalType.WEEK -> "Week ${earliestDate.weekOfYear1}"
                IntervalType.MONTH ->
                    earliestDate.format(DatePickerConstants.TIME_FORMAT_MONTH)
                IntervalType.YEAR -> earliestDate.format(DatePickerConstants.TIME_FORMAT_YEAR)
            }

            labelList.add(label)

            earliestDate = earliestDate.plus(interval)

            amountList.add(BarEntry(summary.amount.toFloat(), yValue))
            countList.add(BarEntry(summary.count.toFloat(), yValue))
        }

        return HistogramEntry(amountList, countList, labelList)
    }
}