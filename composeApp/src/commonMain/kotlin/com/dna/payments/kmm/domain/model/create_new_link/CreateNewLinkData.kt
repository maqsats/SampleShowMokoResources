package com.dna.payments.kmm.domain.model.create_new_link

import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkStatus
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
data class CreateNewLinkData(
    val id: String,
    val amount: Double,
    val createdDate: String,
    val currency: String,
    val customerName: String,
    val description: String,
    val expirationDate: String,
    val createdBy: String?,
    val orderNumber: String,
    val status: PaymentLinkStatus,
    val terminalId: String,
    val url: String,
) : Parcelable