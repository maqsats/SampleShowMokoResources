package com.dna.payments.kmm.domain.model.stores

data class CardSettings(
    val allowRecurring: Boolean,
    val recurringByDefault: Boolean,
    val transactionTypeSelection: Boolean,
    val allowNonCvv: Boolean = false
)
