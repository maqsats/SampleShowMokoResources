package com.dnapayments.mp.domain.model.payment_methods.setting

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
enum class PaymentMethodType(val key: String, val url: String) : Parcelable {
    APPLE_PAY("applePay", "applepay"),
    GOOGLE_PAY("googlepay", "googlepay"),
    KLARNA("klarna", "klarna"),
    PAY_BY_BANK("payByBankApp", "paybybankapp"),
    PAY_PALL("paypal", "paypal"),
    OPEN_BANK("ecospend", "ecospend")
}