package com.dna.payments.kmm.domain.interactors.use_cases.reports

import com.dna.payments.kmm.data.model.overview.SummaryOnlinePaymentsApiModel
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.DATE_FORMAT_WITHOUT_HOUR
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.DATE_FORMAT_WITH_HOUR
import com.dna.payments.kmm.domain.model.date_picker.IntervalType
import com.dna.payments.kmm.domain.model.reports.BarEntry
import com.dna.payments.kmm.domain.model.reports.HistogramEntry
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.TransactionRepository
import com.dna.payments.kmm.utils.extension.convertToServerFormat
import com.dna.payments.kmm.utils.extension.daysBetween
import com.soywiz.klock.DateFormat
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeSpan
import com.soywiz.klock.parse
import com.soywiz.klock.weekOfYear1

class DefaultOnlineSummaryGraphUseCase(private val transactionRepository: TransactionRepository) :
    OnlineSummaryGraphUseCase {

    override suspend fun getOnlineSummaryGraph(
        startDate: DateTime?,
        endDate: DateTime?,
        currency: String,
        intervalType: IntervalType,
        status: String
    ): Response<Pair<HistogramEntry, HistogramEntry>> {
        val originalResponse = transactionRepository.getOnlineGraphSummary(
            startDate.convertToServerFormat(),
            endDate.convertToServerFormat(),
            currency,
            intervalType.key,
            status
        )
        val additionalDays =
            if (intervalType == IntervalType.HOUR) 1 else startDate.daysBetween(endDate)

        val newStartDate = startDate?.minus(DateTimeSpan(days = additionalDays))
        val newEndDate = endDate?.minus(DateTimeSpan(days = additionalDays))

        val secondResponse = transactionRepository.getOnlineGraphSummary(
            newStartDate.convertToServerFormat(),
            newEndDate.convertToServerFormat(),
            currency,
            intervalType.key,
            status
        )

        return when {
            originalResponse is Response.Success && secondResponse is Response.Success -> {
                val originalData = originalResponse.data
                val secondData = secondResponse.data

                Response.Success(
                    Pair(
                        convertToBarEntries(
                            originalData,
                            intervalType
                        ),
                        convertToBarEntries(
                            secondData,
                            intervalType
                        )
                    )
                )
            }
            originalResponse is Response.Error -> Response.Error(originalResponse.error)
            secondResponse is Response.Error -> Response.Error(secondResponse.error)
            originalResponse is Response.TokenExpire -> Response.TokenExpire
            secondResponse is Response.TokenExpire -> Response.TokenExpire
            else -> Response.NetworkError
        }
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
        println("summaryList: $summaryList")
        println("amountList: $amountList")
        println("countList: $countList")
        return HistogramEntry(amountList, countList, labelList)
    }
}