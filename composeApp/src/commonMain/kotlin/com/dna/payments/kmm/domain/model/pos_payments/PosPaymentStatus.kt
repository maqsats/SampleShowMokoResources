package com.dna.payments.kmm.domain.model.pos_payments

enum class PosPaymentStatus(
    val key: String?,
    val displayName: String,
    val detailText: String?
) {
    ALL(null, "All", null),
    CHARGE(
        key = "success",
        displayName = "Successful",
        detailText = "Successfully completed"
    ),
    REJECT(
        key = "failed",
        displayName = "Declined",
        detailText = "Issuer or switch is inoperative"
    );

    companion object {
        fun fromString(status: String): PosPaymentStatus? {
            return values().find { it.key == status }
        }
    }
}