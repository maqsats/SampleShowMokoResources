package com.dna.payments.kmm.data.model.create_new_link

import com.dna.payments.kmm.domain.model.create_new_link.CreateNewLinkData
import com.dna.payments.kmm.domain.model.map.Mapper
import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkStatus


class CreateNewLinkMapper : Mapper<CreateNewLinkApiModel, CreateNewLinkData>() {

    override fun mapData(from: CreateNewLinkApiModel): CreateNewLinkData {
        return CreateNewLinkData(
            amount = from.amount,
            createdDate = from.createdDate,
            createdBy = from.initiatorEmail,
            customerName = from.customerName,
            expirationDate = from.expirationDate,
            status = PaymentLinkStatus.fromString(from.status),
            currency = from.currency,
            description = from.description,
            orderNumber = from.invoiceId,
            terminalId = from.terminalId,
            url = from.url,
            id = from.id
        )
    }

}