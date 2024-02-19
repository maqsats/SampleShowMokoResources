package com.dnapayments.mp.domain.interactors.use_cases.reports.issuing_banks

import com.dnapayments.mp.data.model.report.ReportOnlinePaymentsRequest
import com.dnapayments.mp.data.model.report.ReportPosPaymentsRequest
import com.dnapayments.mp.domain.interactors.helper.PieChartHelper.convertResponse
import com.dnapayments.mp.domain.model.currency.Currency
import com.dnapayments.mp.domain.model.online_payments.OnlinePaymentStatus
import com.dnapayments.mp.domain.model.pos_payments.PosPaymentStatus
import com.dnapayments.mp.domain.model.text_switch.DnaOrderByType
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.repository.TransactionRepository
import com.dnapayments.mp.utils.chart.pie.PieChartData
import com.dnapayments.mp.utils.extension.convertToServerFormat
import korlibs.time.DateTime

class GetIssuingBanksUseCase(private val transactionRepository: TransactionRepository) {

    suspend operator fun invoke(
        from: DateTime?,
        to: DateTime?,
        currency: Currency,
        paymentStatus: PosPaymentStatus
    ): Response<Pair<List<PieChartData>, List<PieChartData>>> {

        val originalResponse = transactionRepository.getPosPaymentIssuer(
            ReportPosPaymentsRequest(
                from.convertToServerFormat(),
                to.convertToServerFormat(),
                currency.name,
                DnaOrderByType.AMOUNT.key
            )
        )

        val secondResponse = transactionRepository.getPosPaymentIssuer(
            ReportPosPaymentsRequest(
                from.convertToServerFormat(),
                to.convertToServerFormat(),
                currency.name,
                DnaOrderByType.COUNT.key
            )
        )

        return when {
            originalResponse is Response.Success && secondResponse is Response.Success -> {
                val originalData = originalResponse.data
                val secondData = secondResponse.data

                val dataSource = when (paymentStatus) {
                    PosPaymentStatus.ALL -> Pair(originalData.all, secondData.all)
                    PosPaymentStatus.CHARGE -> Pair(originalData.successful, secondData.successful)
                    PosPaymentStatus.REJECT -> Pair(originalData.failed, secondData.failed)
                }

                Response.Success(
                    Pair(
                        convertResponse(dataSource.first, DnaOrderByType.AMOUNT),
                        convertResponse(dataSource.second, DnaOrderByType.COUNT)
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


    suspend fun getOnlinePaymentIssuingBanks(
        from: DateTime?,
        to: DateTime?,
        currency: Currency,
        paymentStatus: OnlinePaymentStatus
    ): Response<Pair<List<PieChartData>, List<PieChartData>>> {

        val originalResponse = transactionRepository.getOnlinePaymentIssuer(
            ReportOnlinePaymentsRequest(
                from.convertToServerFormat(),
                to.convertToServerFormat(),
                currency.name,
                paymentStatus.name,
                DnaOrderByType.AMOUNT.key
            )
        )

        val secondResponse = transactionRepository.getOnlinePaymentIssuer(
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