package com.dna.payments.kmm.domain.model.date_picker

data class DatePicker(
    val name: String,
    val period: DatePickerPeriod,
    var isChecked: Boolean = false,
)