package com.dna.payments.kmm.data.model.payment_methods

data class NewTerminalStatusRequest(
    val terminalId: String,
    val status: String
)