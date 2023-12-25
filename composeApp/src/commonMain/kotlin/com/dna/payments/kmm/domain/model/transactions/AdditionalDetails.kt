package com.dna.payments.kmm.domain.model.transactions

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
data class AdditionalDetails(
    val paymentStatus: String,
    val refundSupported: Boolean
) : Parcelable