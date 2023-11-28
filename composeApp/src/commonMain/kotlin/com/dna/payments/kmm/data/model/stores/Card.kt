package com.dna.payments.kmm.data.model.stores

data class Card(
    val allowMoto: Boolean,
    val allowMotoNonCvv: Boolean,
    val allowMotoRecurring: Boolean,
    val allowMultipleRefunds: Boolean,
    val allowPblRecurring: Boolean,
    val motoRecurringByDefault: Boolean,
    val motoTransactionTypeSelection: Boolean,
    val pblRecurringByDefault: Boolean,
    val pblTransactionTypeSelection: Boolean
)