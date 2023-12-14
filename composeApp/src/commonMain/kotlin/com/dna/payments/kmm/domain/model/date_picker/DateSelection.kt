package com.dna.payments.kmm.domain.model.date_picker

import com.soywiz.klock.DateTime
import dev.icerock.moko.parcelize.IgnoredOnParcel
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import kotlin.LazyThreadSafetyMode.NONE

@Parcelize
data class DateSelection(
    val startDate: DateTime? = null,
    val endDate: DateTime? = null
) : Parcelable {
    @IgnoredOnParcel
    val daysBetween by lazy(NONE) {
        if (startDate == null || endDate == null) null else {
            val startMillis = startDate.unixMillisLong
            val endMillis = endDate.unixMillisLong
            val millisecondsPerDay = 24 * 60 * 60 * 1000
            ((endMillis - startMillis) / millisecondsPerDay).toInt()
        }
    }

    override fun toString(): String {
        return "DateSelection(startDate=$startDate, endDate=$endDate)"
    }
}