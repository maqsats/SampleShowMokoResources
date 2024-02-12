package com.dna.payments.kmm.utils.date_picker.utils.extensions

import korlibs.time.Date
import korlibs.time.DateTime
import korlibs.time.Year

const val ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val MONTH_YEAR = "MMMM YYYY"

fun DateTime.toMonthYear(): String {
    return this.asString(MONTH_YEAR).firstLetterUppercase()
}

fun DateTime.asString(format: String = ISO8601): String {
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
    return this.year < date.year || (this.year == date.year && this.dayOfYear < date.dayOfYear)
}

fun Date.isAfter(date: Date): Boolean {
    return this.year > date.year || (this.year == date.year && this.dayOfYear > date.dayOfYear)
}

fun Date.isToday(date: Date): Boolean {
    return this.dayOfYear == date.dayOfYear && this.year == date.year
}