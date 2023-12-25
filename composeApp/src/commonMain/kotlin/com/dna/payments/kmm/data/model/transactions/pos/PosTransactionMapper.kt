package com.dna.payments.kmm.data.model.transactions.pos

import com.dna.payments.kmm.domain.model.map.Mapper
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
            captureMethod = from.captureMethod ?: "",
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
            status = from.status ?: "",
            terminalId = from.terminalId ?: "",
            transactionCity = from.transactionCity ?: "",
            transactionCountry = from.transactionCountry ?: "",
            transactionDate = from.transactionDate ?: "",
            transactionDetails = from.transactionDetails ?: "",
            transactionId = from.transactionId ?: "",
            transactionType = from.transactionType ?: ""
        )
    }
}