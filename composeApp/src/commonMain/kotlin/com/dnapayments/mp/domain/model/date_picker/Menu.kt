package com.dnapayments.mp.domain.model.date_picker

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
enum class Menu : Parcelable {
    OVERVIEW,
    SETTLEMENTS,
    ONLINE_PAYMENTS,
    POS_PAYMENTS,
    REPORTS,
    PAYMENT_LINKS
}