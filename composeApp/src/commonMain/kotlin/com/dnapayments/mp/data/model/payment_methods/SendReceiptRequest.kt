package com.dnapayments.mp.data.model.payment_methods

import kotlinx.serialization.Serializable

@Serializable
data class SendReceiptRequest(
    val email: String
)
