package com.dna.payments.kmm.domain.model.status_summary


enum class PaymentStatus(
    val displayName: String,
) {
    ALL("All"),
    AUTH("Pending"),
    CREATED("Created"),
    CREDITED("Credited"),
    CHARGE("Charged"),
    CANCEL("Cancelled"),
    REFUND("Refunded"),
    REJECT("Declined"),
    FAILED("Failed"),
    NEW("New"),
    THREE_D_SECURE("3D Secure"),
    TOKENIZED("Tokenized"),
    VERIFIED("Verified"),
    PROCESSING("Processing"),
    ABANDONED("Abandoned");

    companion object {
        fun fromString(status: String? = "ALL"): PaymentStatus? {
            return values().find { it.name == status }
        }
    }
}