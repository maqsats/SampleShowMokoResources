package com.dna.payments.kmm.domain.model.payment_links

import com.dna.payments.kmm.utils.UiText
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
data class PaymentLinkItem(
    val amount: Double,
    val currency: String,
    val customerName: String,
    val description: String,
    val initiatorEmail: String?,
    val expirationDate: String,
    val createdDate: String,
    val invoiceId: String,
    val paidDate: String,
    val status: PaymentLinkStatus,
    val url: String,
    val id: String,
    val viewedDate: String
) : Parcelable, PaymentLinkByPeriod

@Parcelize
data class PaymentLinkHeader(
    val title : UiText
) : Parcelable, PaymentLinkByPeriod

interface PaymentLinkByPeriod