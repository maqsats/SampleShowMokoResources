package com.dnapayments.mp.utils.date_picker.utils.extensions

fun String.firstLetterUppercase(): String {
    return this.replaceFirstChar { it.uppercase() }
}