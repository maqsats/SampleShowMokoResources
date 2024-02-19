package com.dnapayments.mp.domain.model.pos_payments;

import com.dnapayments.mp.MR
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

enum class PosPaymentCard(
    val stringResource: StringResource,
    val imageResource: ImageResource?,
    val cardScheme: String,
    val cardType: String,
) {
    MASTER_CARD(MR.strings.mastercard, MR.images.ic_mastercard, "mastercard", "MasterCard"),
    VISA(MR.strings.visa, MR.images.ic_visa, "visa", "VISA"),
    UPI(MR.strings.upi, MR.images.ic_unionpay, "upi", "UnionPay"),
    UNION_PAY(MR.strings.upi, MR.images.ic_unionpay, "unionpay", "UnionPay"),
    AMERICAN_EXPRESS(
        MR.strings.amex,
        MR.images.ic_amex,
        "americanexpress",
        "AmericanExpress"
    ),
    DINERS_CLUB(MR.strings.diners, MR.images.ic_dc, "diners", "Diners"),
    UNDEFINED(MR.strings.empty_text, null, "", "");


    companion object {
        fun fromCardScheme(string: String): PosPaymentCard {
            return PosPaymentCard.values().find { it.cardScheme == string }
                ?: UNDEFINED
        }

        fun fromCardType(string: String): PosPaymentCard {
            return PosPaymentCard.values().find { it.cardType == string }
                ?: UNDEFINED
        }
    }
}