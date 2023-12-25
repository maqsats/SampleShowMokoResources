package com.dna.payments.kmm.data.model.transactions

import com.dna.payments.kmm.domain.model.map.Mapper
import com.dna.payments.kmm.domain.model.transactions.AdditionalDetails
import com.dna.payments.kmm.domain.model.transactions.Transaction
import com.dna.payments.kmm.domain.model.transactions.TransactionPayload


class TransactionPayLoadMapper : Mapper<TransactionPayloadApiModel, TransactionPayload>() {

    override fun mapData(from: TransactionPayloadApiModel): TransactionPayload {
        return TransactionPayload(
            totalCount = from.totalCount,
            records = mapRecords(from.records)
        )
    }

    private fun mapRecords(records: List<TransactionApiModel>?): List<Transaction>? {
        return records?.map { mapItem(it) }
    }

    private fun mapItem(from: TransactionApiModel): Transaction {
        return Transaction(
            accountId = from.accountId ?: "",
            acquirerResponseCode = from.acquirerResponseCode ?: "",
            additionalDetails = mapAdditionDetails(from.additionalDetails),
            amount = from.amount ?: 0.0,
            amountBonus = from.amountBonus ?: 0,
            authCode = from.authCode ?: "",
            authDate = from.authDate ?: "",
            avsHouseNumberResult = from.avsHouseNumberResult ?: "",
            avsPostcodeResult = from.avsPostcodeResult ?: "",
            avsResult = from.avsResult ?: "",
            balance = from.balance ?: 0.0,
            cardExpiryDate = from.cardExpiryDate ?: "",
            cardMask = from.cardMask ?: "",
            cardType = from.cardType,
            client = from.client ?: "",
            confirmDate = from.confirmDate ?: "",
            createdDate = from.createdDate ?: "",
            cscResult = from.cscResult ?: "",
            currency = from.currency ?: "",
            data = from.data ?: "",
            description = from.description ?: "",
            donationAmount = from.donationAmount ?: 0.0,
            failureLink = from.failureLink ?: "",
            id = from.id ?: "",
            invoiceId = from.invoiceId ?: "",
            ipCity = from.ipCity ?: "",
            ipCountry = from.ipCountry ?: "",
            ipDistrict = from.ipDistrict ?: "",
            ipLatitude = from.ipLatitude ?: 0.0,
            ipLongitude = from.ipLongitude ?: 0.0,
            ipRegion = from.ipRegion ?: "",
            issuer = from.issuer ?: "",
            issuerBankCountry = from.issuerBankCountry ?: "",
            language = from.language ?: "",
            merchant = from.merchant ?: "",
            paAuthentication = from.paAuthentication ?: "",
            paEnrollment = from.paEnrollment ?: "",
            payerEmail = from.payerEmail ?: "",
            payerIp = from.payerIp ?: "",
            payerName = from.payerName ?: "",
            payerPhone = from.payerPhone ?: "",
            paymentMethod = from.paymentMethod ?: "",
            payoutAmount = from.payoutAmount ?: 0.0,
            payoutDate = from.payoutDate ?: "",
            postLink = from.postLink ?: "",
            postLinkStatus = from.postLinkStatus ?: false,
            processingTypeId = from.processingTypeId ?: 0,
            processingTypeName = from.processingTypeName ?: "",
            reference = from.reference ?: "",
            resultMessage = from.resultMessage ?: "",
            secure3D = from.secure3D ?: false,
            shop = from.shop ?: "",
            status = from.status ?: "ALL",
            transactionType = from.transactionType ?: ""
        )
    }


    private fun mapAdditionDetails(from: AdditionalDetailsApiModel?) = AdditionalDetails(
        paymentStatus = from?.paymentStatus ?: "",
        refundSupported = from?.refundSupported ?: false
    )
}