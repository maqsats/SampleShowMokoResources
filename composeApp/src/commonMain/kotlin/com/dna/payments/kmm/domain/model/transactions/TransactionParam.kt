package com.dna.payments.kmm.domain.model.transactions

import com.dna.payments.kmm.domain.model.status_summary.PaymentStatus
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
data class TransactionParam(
    var amount: Double,
    var status: PaymentStatus,
    val currency: String,
    val id: String
) : Parcelable {
    companion object {
        fun newInstance(from: Transaction): TransactionParam = TransactionParam(
            amount = from.amount,
            currency = from.currency,
            id = from.id,
            status = from.status
        )
    }
}