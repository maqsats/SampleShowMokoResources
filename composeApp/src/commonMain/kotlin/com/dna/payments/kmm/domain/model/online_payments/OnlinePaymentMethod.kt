package com.dna.payments.kmm.domain.model.online_payments;

import com.dna.payments.kmm.MR
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

enum class OnlinePaymentMethod(
    val stringResource: StringResource,
    val imageResource: ImageResource?,
    val value: String
) {
    APPLE_PAY(MR.strings.apple_pay, MR.images.ic_applepay, "applepay"),
    GOOGLE_PAY(MR.strings.google_pay, MR.images.ic_googlepay, "googlepay"),
    PAY_PAL(MR.strings.pay_pall, MR.images.ic_paypal, "paypal"),
    PAY_BY_BANK(MR.strings.pay_by_bank, MR.images.ic_paybybank, "payByBankApp"),
    OPEN_BANK(MR.strings.open_bank, MR.images.ic_openbanking, "openbank"),
    KLARNA(MR.strings.klarna, MR.images.ic_klarna, "klarna"),
    ASTROPAY(MR.strings.astro_pay, MR.images.ic_astropay, "astropay"),
    ECOSPEND(MR.strings.ecospend, MR.images.ic_ecospend, "ecospend"),
    CLICK_TO_PAY(MR.strings.clicktopay, MR.images.ic_clicktopay, "clicktopay"),
    CARD(MR.strings.card, null, "card"),
    UNDEFINED(MR.strings.empty_text, null, "");

    companion object {
        fun fromString(string: String): OnlinePaymentMethod {
            return OnlinePaymentMethod.values().find { it.value == string }
                ?: UNDEFINED
        }
    }
}
