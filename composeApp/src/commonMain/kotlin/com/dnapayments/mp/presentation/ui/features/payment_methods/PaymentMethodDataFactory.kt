package com.dnapayments.mp.presentation.ui.features.payment_methods

import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.payment_methods.PaymentMethod
import com.dnapayments.mp.domain.model.payment_methods.setting.PaymentMethodType

object PaymentMethodDataFactory {
    fun getPaymentMethods(): List<PaymentMethod> = listOf(
        PaymentMethod(
            MR.strings.apple_pay,
            MR.images.ic_applepay,
            MR.strings.apple_pay_description,
            PaymentMethodType.APPLE_PAY
        ),
        PaymentMethod(
            MR.strings.google_pay,
            MR.images.ic_googlepay,
            MR.strings.google_pay_description,
            PaymentMethodType.GOOGLE_PAY
        ),
        PaymentMethod(
            MR.strings.pay_pall,
            MR.images.ic_paypal,
            MR.strings.pay_pal_description,
            PaymentMethodType.PAY_PALL
        ),
        PaymentMethod(
            MR.strings.pay_by_bank,
            MR.images.ic_paybybank,
            MR.strings.open_by_bank_description,
            PaymentMethodType.PAY_BY_BANK
        ),
        PaymentMethod(
            MR.strings.open_bank,
            MR.images.ic_openbanking,
            MR.strings.open_by_bank_description,
            PaymentMethodType.OPEN_BANK
        ),
        PaymentMethod(
            MR.strings.klarna,
            MR.images.ic_klarna,
            MR.strings.klarna_description,
            PaymentMethodType.KLARNA
        )
    )
}