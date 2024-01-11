package com.dna.payments.kmm.domain.interactors.use_cases.reports

import com.dna.payments.kmm.data.model.overview.SummaryPosApiModel
import com.dna.payments.kmm.data.model.overview.SummaryPosRequest
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants
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

class DefaultPosSummaryGraphUseCase(private val transactionRepository: TransactionRepository) :
    PosSummaryGraphUseCase {

    override suspend fun getPosSummaryGraph(
        startDate: DateTime?,
        endDate: DateTime?,
        currency: String,
        intervalType: IntervalType
    ): Response<Pair<HistogramEntry, HistogramEntry>> {

        val originalResponse = transactionRepository.getPosGraphSummary(
            SummaryPosRequest(
                startDate.convertToServerFormat(),
                endDate.convertToServerFormat(),
                intervalType.key,
                currency
            )
        )
        val additionalDays =
            if (intervalType == IntervalType.HOUR) 1 else startDate.daysBetween(endDate)

        val newStartDate = startDate?.minus(DateTimeSpan(days = additionalDays))
        val newEndDate = endDate?.minus(DateTimeSpan(days = additionalDays))

        val secondResponse = transactionRepository.getPosGraphSummary(
            SummaryPosRequest(
                newStartDate.convertToServerFormat(),
                newEndDate.convertToServerFormat(),
                intervalType.key,
                currency
            )
        )

        return when {
            originalResponse is Response.Success && secondResponse is Response.Success -> {
                val originalData = originalResponse.data
                val secondData = secondResponse.data

                Response.Success(
                    Pair(
                        convertToBarEntries(
                            originalData.all,
                            intervalType
                        ),
                        convertToBarEntries(
                            secondData.all,
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