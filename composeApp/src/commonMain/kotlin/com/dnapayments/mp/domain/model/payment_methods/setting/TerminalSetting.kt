package com.dnapayments.mp.domain.model.payment_methods.setting

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
data class TerminalSetting(
    val id: String,
    val name: String,
    val paymentMethodType: PaymentMethodType,
    val countTerminal: Int,
    val activeTerminal: Int,
    val detailTerminalSettingList: List<DetailTerminalSetting>
) : Parcelable