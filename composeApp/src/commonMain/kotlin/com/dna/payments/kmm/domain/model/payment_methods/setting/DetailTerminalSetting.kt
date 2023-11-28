package com.dna.payments.kmm.domain.model.payment_methods.setting

data class DetailTerminalSetting(
    val id: String,
    val paymentTypeUrl: PaymentMethodType,
    val status: Boolean
)
