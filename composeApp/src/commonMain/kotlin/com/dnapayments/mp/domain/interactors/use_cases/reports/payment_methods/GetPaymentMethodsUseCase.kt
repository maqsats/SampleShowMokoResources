package com.dnapayments.mp.domain.interactors.use_cases.reports.payment_methods

import com.dnapayments.mp.data.model.report.ReportOnlinePaymentsRequest
import com.dnapayments.mp.domain.interactors.helper.PieChartHelper.convertResponse
import com.dnapayments.mp.domain.model.currency.Currency
import com.dnapayments.mp.domain.model.online_payments.OnlinePaymentStatus
import com.dnapayments.mp.domain.model.text_switch.DnaOrderByType
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.TransactionRepository
import com.dnapayments.mp.utils.chart.pie.PieChartData
import com.dnapayments.mp.utils.extension.convertToServerFormat
import korlibs.time.DateTime

class GetPaymentMethodsUseCase(private val transactionRepository: TransactionRepository) {

    suspend fun getPaymentMethods(
        from: DateTime?,
        to: DateTime?,
        currency: Currency,
        paymentStatus: OnlinePaymentStatus
    ): Response<Pair<List<PieChartData>, List<PieChartData>>> {

        val originalResponse = transactionRepository.getOnlinePaymentMethods(
            ReportOnlinePaymentsRequest(
                from.convertToServerFormat(),
                to.convertToServerFormat(),
                currency.name,
                paymentStatus.name,
                DnaOrderByType.AMOUNT.key
            )
        )

        val secondResponse = transactionRepository.getOnlinePaymentMethods(
            ReportOnlinePaymentsRequest(
                from.convertToServerFormat(),
                to.convertToServerFormat(),
                currency.name,
                paymentStatus.name,
                DnaOrderByType.COUNT.key
            )
        )

        return when {
            originalResponse is Response.Success && secondResponse is Response.Success -> {
                Response.Success(
                    Pair(
                        convertResponse(originalResponse.data, DnaOrderByType.AMOUNT),
                        convertResponse(secondResponse.data, DnaOrderByType.COUNT)
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
}