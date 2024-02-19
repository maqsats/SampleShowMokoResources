package com.dnapayments.mp.domain.model.pos_payments

import com.dnapayments.mp.domain.model.payment_status.PaymentStatus

enum class PosPaymentStatus(
    val key: String,
    val displayName: String,
    val detailText: String?
) : PaymentStatus {
    ALL("all", "All statuses", null),
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
            return entries.find { it.key == status }
        }
    }
}