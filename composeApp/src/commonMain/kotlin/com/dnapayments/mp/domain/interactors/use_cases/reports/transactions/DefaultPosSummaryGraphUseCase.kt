package com.dnapayments.mp.domain.interactors.use_cases.reports.transactions

import com.dnapayments.mp.data.model.overview.SummaryPosApiModel
import com.dnapayments.mp.data.model.overview.SummaryPosRequest
import com.dnapayments.mp.domain.interactors.use_cases.date_picker.DatePickerConstants
import com.dnapayments.mp.domain.model.date_picker.IntervalType
import com.dnapayments.mp.domain.model.pos_payments.PosPaymentStatus
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.TransactionRepository
import com.dnapayments.mp.utils.chart.histogram.BarEntry
import com.dnapayments.mp.utils.chart.histogram.HistogramEntry
import com.dnapayments.mp.utils.extension.convertToServerFormat
import com.dnapayments.mp.utils.extension.daysBetween
import korlibs.time.DateFormat
import korlibs.time.DateTime
import korlibs.time.DateTimeSpan
import korlibs.time.parse
import korlibs.time.weekOfYear1

class DefaultPosSummaryGraphUseCase(private val transactionRepository: TransactionRepository) :
    PosSummaryGraphUseCase {

    override suspend fun getPosSummaryGraph(
        startDate: DateTime?,
        endDate: DateTime?,
        currency: String,
        intervalType: IntervalType,
        status: PosPaymentStatus
    ): Response<Pair<HistogramEntry, HistogramEntry>> {

        val originalResponse = transactionRepository.getPosGraphSummary(
            SummaryPosRequest(
                startDate.convertToServerFormat(),
                endDate.convertToServerFormat(),
                intervalType.key,
                currency,
                status.key
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
                currency,
                status.key
            )
        )

        return when {
            originalResponse is Response.Success && secondResponse is Response.Success -> {
                val originalData = originalResponse.data
                val secondData = secondResponse.data

                val dataSource = when (status) {
                    PosPaymentStatus.ALL -> Pair(originalData.all, secondData.all)
                    PosPaymentStatus.CHARGE -> Pair(originalData.successful, secondData.successful)
                    PosPaymentStatus.REJECT -> Pair(originalData.failed, secondData.failed)
                }

                Response.Success(
                    Pair(
                        convertToBarEntries(
                            dataSource.first,
                            intervalType
                        ),
                        convertToBarEntries(
                            dataSource.second,
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