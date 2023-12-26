package com.dna.payments.kmm.domain.interactors.use_cases.transaction

import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.DatePickerConstants.ZERO_DOUBLE_VALUE
import com.dna.payments.kmm.domain.model.average_metrics.Metric
import com.dna.payments.kmm.domain.model.average_metrics.MetricDescription
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentStatus
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentSummary
import com.dna.payments.kmm.domain.model.status_summary.PaymentStatus
import com.dna.payments.kmm.domain.model.status_summary.Summary
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.repository.TransactionRepository
import com.dna.payments.kmm.utils.extension.convertToServerFormat
import com.dna.payments.kmm.utils.extension.daysBetween
import com.soywiz.klock.DateTime
import com.soywiz.klock.DateTimeSpan

class DefaultTransactionUseCase(private val transactionRepository: TransactionRepository) :
    TransactionUseCase {

    override suspend fun getMainSummary(
        startDate: DateTime?,
        endDate: DateTime?,
        currency: String
    ): Response<List<Summary>> {
        val originalResponse = transactionRepository.getStatusSummary(
            startDate.convertToServerFormat(),
            endDate.convertToServerFormat(),
            currency
        )
        val additionalDays = startDate.daysBetween(endDate)

        val newStartDate = startDate?.minus(DateTimeSpan(days = additionalDays))
        val newEndDate = endDate?.minus(DateTimeSpan(days = additionalDays))

        val secondResponse = transactionRepository.getStatusSummary(
            newStartDate.convertToServerFormat(),
            newEndDate.convertToServerFormat(),
            currency
        )

        return when {
            originalResponse is Response.Success && secondResponse is Response.Success -> {
                val originalData = originalResponse.data
                val secondData = secondResponse.data

                val originalMap = originalData.associateBy { it.status }
                val secondMap = secondData.associateBy { it.status }

                val percentageChanges = mutableMapOf<PaymentStatus, Double>()

                for (status in PaymentStatus.values()) {
                    val originalAmount = originalMap[status]?.amount ?: 0.0
                    val secondAmount = secondMap[status]?.amount ?: 0.0

                    val percentageChange = calculatePercentageChange(
                        originalAmount,
                        secondAmount
                    )

                    percentageChanges[status] = percentageChange
                }

                val resultData = originalData.map { paymentSummary ->
                    val status = paymentSummary.status
                    val percentage = percentageChanges[status] ?: 0.0
                    paymentSummary.copy(percentage = percentage)
                }

                Response.Success(filterMainSummary(resultData))
            }
            originalResponse is Response.Error ->
                Response.Error(originalResponse.error)
            secondResponse is Response.Error ->
                Response.Error(secondResponse.error)
            else -> Response.NetworkError
        }
    }

    private fun filterMainSummary(summaryList: List<Summary>): List<Summary> {
        val desiredStatuses = listOf(
            PaymentStatus.CHARGE,
            PaymentStatus.AUTH,
            PaymentStatus.CANCEL,
            PaymentStatus.REFUND
        )

        return summaryList.filter { it.status in desiredStatuses }
            .sortedBy { desiredStatuses.indexOf(it.status) }
    }

    override suspend fun getPosPaymentsSummary(
        startDate: DateTime?,
        endDate: DateTime?,
        currency: String,
    ): Response<List<PosPaymentSummary>> {
        val originalResponse = transactionRepository.getPosPaymentStatusSummary(
            startDate.convertToServerFormat(),
            endDate.convertToServerFormat(),
            currency
        )
        val additionalDays = startDate.daysBetween(endDate)

        val newStartDate = startDate?.minus(DateTimeSpan(days = additionalDays))
        val newEndDate = endDate?.minus(DateTimeSpan(days = additionalDays))

        val secondResponse = transactionRepository.getPosPaymentStatusSummary(
            newStartDate.convertToServerFormat(),
            newEndDate.convertToServerFormat(),
            currency
        )

        return when {
            originalResponse is Response.Success && secondResponse is Response.Success -> {
                val originalData = originalResponse.data
                val secondData = secondResponse.data

                val originalMap = originalData.associateBy { it.status }
                val secondMap = secondData.associateBy { it.status }

                val percentageChanges = mutableMapOf<PosPaymentStatus, Double>()

                for (status in PosPaymentStatus.values()) {
                    val originalAmount = originalMap[status]?.amount ?: 0.0
                    val secondAmount = secondMap[status]?.amount ?: 0.0

                    percentageChanges[status] = calculatePercentageChange(
                        originalAmount,
                        secondAmount
                    )
                }

                val resultData = originalData.map { paymentSummary ->
                    paymentSummary.copy(
                        percentage = percentageChanges[paymentSummary.status] ?: 0.0
                    )
                }

                Response.Success(filterPosPaymentSummary(resultData))
            }
            originalResponse is Response.TokenExpire ->
                Response.TokenExpire
            secondResponse is Response.TokenExpire ->
                Response.TokenExpire
            originalResponse is Response.Error ->
                Response.Error(originalResponse.error)
            secondResponse is Response.Error ->
                Response.Error(secondResponse.error)
            else -> Response.NetworkError
        }
    }

    private fun filterPosPaymentSummary(data: List<PosPaymentSummary>): List<PosPaymentSummary> {
        val desiredStatuses = listOf(
            PosPaymentStatus.ALL,
            PosPaymentStatus.CHARGE,
            PosPaymentStatus.REJECT
        )
        return data.filter { it.status in desiredStatuses }
            .sortedBy { desiredStatuses.indexOf(it.status) }
    }

    override fun getAverageMetricsOnlinePayments(
        summaryList: List<Summary>,
        daysCount: Int?
    ): List<Metric> {
        if (daysCount == null || daysCount == 0) return getEmptyMetricList()

        val chargePaymentMethod =
            summaryList.firstOrNull { it.status == PaymentStatus.CHARGE }
                ?: return getEmptyMetricList()

        return listOf(
            Metric(
                if (chargePaymentMethod.count == 0)
                    ZERO_DOUBLE_VALUE else chargePaymentMethod.amount / chargePaymentMethod.count,
                MetricDescription.SUCCESSFUL_AVERAGE
            ),
            Metric(
                chargePaymentMethod.amount / daysCount.toInt(),
                MetricDescription.SUCCESSFUL_DAILY_AVERAGE
            ),
            Metric(
                (chargePaymentMethod.count / daysCount.toInt()).toDouble(),
                MetricDescription.SUCCESSFUL_DAILY_AVERAGE_NUMBER
            )
        )
    }

    private fun calculatePercentageChange(firstValue: Double, secondValue: Double): Double {
        if (secondValue == 0.0 && firstValue > 0.0) return 0.0
        if (firstValue == 0.0 && secondValue > 0.0) return -100.0
        if (firstValue == 0.0 && secondValue == 0.0) return 0.0
        return ((secondValue - firstValue) / secondValue) * 100 * -1
    }

    override fun getAverageMetricsPosPayments(
        summaryList: List<PosPaymentSummary>,
        daysCount: Int?
    ): List<Metric> {
        if (daysCount == null || daysCount == 0) return getEmptyMetricList()

        val successful =
            summaryList.firstOrNull { it.status == PosPaymentStatus.CHARGE }
                ?: return getEmptyMetricList()

        return listOf(
            Metric(
                if (successful.count == 0)
                    ZERO_DOUBLE_VALUE else successful.amount / successful.count,
                MetricDescription.SUCCESSFUL_AVERAGE
            ),
            Metric(
                successful.amount / daysCount.toInt(),
                MetricDescription.SUCCESSFUL_DAILY_AVERAGE
            ),
            Metric(
                (successful.count / daysCount.toInt()).toDouble(),
                MetricDescription.SUCCESSFUL_DAILY_AVERAGE_NUMBER
            )
        )
    }

    override fun getOrderedPaymentStatus(): List<PaymentStatus> =
        PaymentStatus.values().toList()

    private fun getEmptyMetricList() = listOf(
        Metric(
            ZERO_DOUBLE_VALUE,
            MetricDescription.SUCCESSFUL_AVERAGE
        ),
        Metric(
            ZERO_DOUBLE_VALUE,
            MetricDescription.SUCCESSFUL_DAILY_AVERAGE
        ),
        Metric(
            ZERO_DOUBLE_VALUE,
            MetricDescription.SUCCESSFUL_DAILY_AVERAGE_NUMBER
        )
    )
}