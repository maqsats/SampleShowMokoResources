package com.dna.payments.kmm.data.model.payment_links

import com.dna.payments.kmm.domain.model.map.Mapper
import com.dna.payments.kmm.domain.model.payment_links.PaymentLink
import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkItem
import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkStatus


class PaymentLinkMapper : Mapper<PaymentLinkApiModel, PaymentLink>() {

    override fun mapData(from: PaymentLinkApiModel): PaymentLink {
        return PaymentLink(
            mapItem(from.records), from.totalCount
        )
    }

    private fun mapItem(from: List<PaymentLinkItemApiModel>): List<PaymentLinkItem> {
        return from.map {
            PaymentLinkItem(
                amount = it.amount,
                currency = it.currency,
                customerName = it.customerName,
                description = it.description,
                initiatorEmail = it.initiatorEmail,
                invoiceId = it.invoiceId,
                paidDate = it.paidDate.orEmpty(),
                status = PaymentLinkStatus.fromString(it.status),
                url = it.url,
                viewedDate = it.viewedDate.orEmpty(),
                id = it.id,
                expirationDate = it.expirationDate,
                createdDate = it.createdDate
            )
        }
    }
}