package com.dnapayments.mp.data.model.stores

import kotlinx.serialization.Serializable

@Serializable
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