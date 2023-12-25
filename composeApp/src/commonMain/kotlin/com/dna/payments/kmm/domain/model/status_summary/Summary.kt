package com.dna.payments.kmm.domain.model.status_summary

data class Summary(
    val amount: Double,
    val count: Int,
    val status: PaymentStatus,
    var percentage: Double = 0.0
) {
    override fun toString(): String {
        return "Summary(amount=$amount, count=$count, status=$status)"
    }
}