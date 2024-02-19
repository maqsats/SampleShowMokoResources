package com.dnapayments.mp.domain.model.pos_payments

sealed class CaptureMethod(val key: String, val value: String) {
    object PosContactless : CaptureMethod("pos-contactless", "POS Contactless")
    object PayByBankApp : CaptureMethod("ecom-paybybankapp", "Pay by Bank app")
    object OpenBanking : CaptureMethod("ecom-openbanking", "Open Banking")
    object PosOpenBanking : CaptureMethod("pos-openbanking", "Open Banking")
    object Klarna : CaptureMethod("ecom-klarna", "Klarna")
    object Ecom : CaptureMethod("ecom", "ECOM")
    object Pos : CaptureMethod("pos", "POS")
    object Stored : CaptureMethod("stored", "Stored")
    object Moto : CaptureMethod("moto", "MOTO")
    object Recurring : CaptureMethod("recurring", "Recurring")

    companion object {
        fun fromKey(key: String): String {
            return when (key) {
                PosContactless.key -> PosContactless.value
                PayByBankApp.key -> PayByBankApp.value
                OpenBanking.key -> OpenBanking.value
                PosOpenBanking.key -> PosOpenBanking.value
                Klarna.key -> Klarna.value
                Ecom.key -> Ecom.value
                Pos.key -> Pos.value
                Stored.key -> Stored.value
                Moto.key -> Moto.value
                Recurring.key -> Recurring.value
                else -> "-"
            }
        }
    }
}
