package com.dna.payments.kmm.domain.interactors.use_cases.reports

import com.dna.payments.kmm.data.model.overview.SummaryOnlinePaymentsApiModel
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.DATE_FORMAT_WITH_HOUR
import com.dna.payments.kmm.domain.model.date_picker.IntervalType
import com.dna.payments.kmm.domain.model.reports.BarEntry
import com.dna.payments.kmm.domain.model.reports.HistogramEntry
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.TransactionRepository
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTimeSpan
import com.soywiz.klock.parse
import com.soywiz.klock.weekOfYear1

class DefaultOnlineSummaryGraphUseCase(private val transactionRepository: TransactionRepository) :
    OnlineSummaryGraphUseCase {

    override suspend fun getOnlineSummaryGraph(
        startDate: String,
        endDate: String,
        currency: String,
        interval: String,
        status: String, intervalType: IntervalType
    ): Response<HistogramEntry> =
        when (val response = transactionRepository.getOnlineGraphSummary(
            startDate,
            endDate,
            currency,
            interval,
            status
        )) {
            is Response.Error -> Response.Error(response.error)
            is Response.NetworkError -> Response.NetworkError
            is Response.TokenExpire -> Response.TokenExpire
            is Response.Success -> Response.Success(
                convertToBarEntries(
                    response.data,
                    intervalType
                )
            )
        }

    private fun convertToBarEntries(
        summaryList: List<SummaryOnlinePaymentsApiModel>,
        intervalType: IntervalType
    ): HistogramEntry {

        val sortedList = summaryList.sortedBy { it.date }

        val dateFormat = DateFormat(
            DATE_FORMAT_WITH_HOUR
        )

        var earliestDate = dateFormat.parse(sortedList.first().date)

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