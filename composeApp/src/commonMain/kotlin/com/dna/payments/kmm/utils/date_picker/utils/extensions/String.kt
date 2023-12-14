package com.dna.payments.kmm.utils.date_picker.utils.extensions

fun String.firstLetterUppercase(): String {
    return this.replaceFirstChar { it.uppercase() }
}