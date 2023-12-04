package com.dna.payments.kmm.domain.model.payment_methods.setting

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize


@Parcelize
data class DetailTerminalSetting(
    val id: String,
    val paymentTypeUrl: PaymentMethodType,
    val status: Boolean,
    val hasPermission: Boolean = true
) : Parcelable
