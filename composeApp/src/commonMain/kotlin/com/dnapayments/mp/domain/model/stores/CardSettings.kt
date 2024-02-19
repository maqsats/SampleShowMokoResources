package com.dnapayments.mp.domain.model.stores

data class CardSettings(
    val allowRecurring: Boolean,
    val recurringByDefault: Boolean,
    val transactionTypeSelection: Boolean,
    val allowNonCvv: Boolean = false
)
