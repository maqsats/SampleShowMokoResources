package com.dna.payments.kmm.data.model.transactions.pos

import com.dna.payments.kmm.domain.model.map.Mapper
import com.dna.payments.kmm.domain.model.online_payments.TransactionType
import com.dna.payments.kmm.domain.model.pos_payments.CaptureMethod
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentStatusV2
import com.dna.payments.kmm.domain.model.transactions.pos.PosTransaction
import com.dna.payments.kmm.domain.model.transactions.pos.PosTransactions


class PosTransactionMapper : Mapper<PosTransactionsApiModel, PosTransactions>() {

    override fun mapData(from: PosTransactionsApiModel): PosTransactions {
        return PosTransactions(
            data = mapRecords(from.data),
            totalCount = from.totalCount ?: 0,
            totalAmount = from.totalAmount ?: 0.0
        )
    }

    private fun mapRecords(records: List<PosTransactionApiModel>?): List<PosTransaction> {
        return records?.map { mapItem(it) } ?: emptyList()
    }

    private fun mapItem(from: PosTransactionApiModel): PosTransaction {
        return PosTransaction(
            amount = from.amount ?: 0.0,
            captureMethod = CaptureMethod.fromKey(from.captureMethod ?: ""),
            cardMask = from.cardMask ?: "",
            cardScheme = from.cardScheme ?: "",
            cardType = from.cardType ?: "",
            currency = from.currency ?: "",
            isCorporateCard = from.isCorporateCard,
            isEuropeanCard = from.isEuropeanCard ?: false,
            issuerRegion = from.issuerRegion ?: "",
            mid = from.mid ?: "",
            operation = from.operation ?: "",
            returnCode = from.returnCode ?: "",
            returnCodeDescription = from.returnCodeDescription ?: "",
            status = PosPaymentStatusV2.fromString(from.status ?: ""),
            terminalId = from.terminalId ?: "",
            transactionCity = from.transactionCity ?: "",
            transactionCountry = from.transactionCountry ?: "",
            transactionDate = from.transactionDate ?: "",
            transactionDetails = from.transactionDetails ?: "",
            transactionId = from.transactionId ?: "",
            transactionType = mapToTransactionType(from.transactionType, from.operation)
        )
    }

    private fun mapToTransactionType(
        transactionType: String?,
        operation: String?
    ): TransactionType {
        return when (transactionType) {
            "retail", "unique" -> when (operation) {
                "request" -> TransactionType.AUTH
                "advice" -> TransactionType.SALE
                "adjustment" -> TransactionType.ADJUSTMENT
                "reversal" -> TransactionType.REFUND
                else -> TransactionType.OTHER
            }

            "credit" -> when (operation) {
                "advice" -> TransactionType.REFUND
                else -> TransactionType.OTHER
            }

            "retail-apm" -> when (operation) {
                "advice" -> TransactionType.SALE
                else -> TransactionType.OTHER
            }

            "credit-notif" -> when (operation) {
                "advice" -> TransactionType.REFUND
                else -> TransactionType.OTHER
            }

            else -> TransactionType.OTHER
        }
    }
}