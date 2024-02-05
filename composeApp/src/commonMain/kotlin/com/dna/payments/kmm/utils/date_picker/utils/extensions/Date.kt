package com.dna.payments.kmm.utils.date_picker.utils.extensions

import korlibs.time.Date
import korlibs.time.DateTime
import korlibs.time.Time
import korlibs.time.Year

const val ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val MONTH_YEAR = "MMMM YYYY"
const val SHORT_DAY = "EEE"

fun Date.toMonthYear(): String {
    return this.asString(MONTH_YEAR).firstLetterUppercase()
}

fun Time.toMonthYear(): String {
    return this.asString(MONTH_YEAR).firstLetterUppercase()
}

fun Date.toShortDay(): String {
    return this.asString(SHORT_DAY).uppercase()
}

fun Time.toShortDay(): String {
    return this.asString(SHORT_DAY).uppercase()
}

fun Date.asString(format: String = ISO8601): String {
    return this.format(format)
}

fun Time.asString(format: String = ISO8601): String {
    return this.format(format)
}

fun DateTime.set(year: Int): DateTime {
    return this.copyDayOfMonth(
        year = Year(year),
        month = this.month,
        dayOfMonth = this.dayOfMonth
    )
}

fun Date.isBefore(date: Date): Boolean {
    return this.dayOfYear < date.dayOfYear && this.year < date.year
}

fun Date.isAfter(date: Date): Boolean {
    return this.dayOfYear > date.dayOfYear && this.year > date.year
}