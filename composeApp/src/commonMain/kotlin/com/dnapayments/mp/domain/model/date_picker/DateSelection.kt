package com.dnapayments.mp.domain.model.date_picker

import korlibs.time.DateTime
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as DateSelection

        if (startDate != other.startDate) return false
        return endDate == other.endDate
    }

    override fun hashCode(): Int {
        var result = startDate?.hashCode() ?: 0
        result = 31 * result + (endDate?.hashCode() ?: 0)
        return result
    }
}