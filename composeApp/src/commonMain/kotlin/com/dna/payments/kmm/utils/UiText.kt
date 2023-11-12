package com.dna.payments.kmm.utils

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class StringResource(
        val stringResource: dev.icerock.moko.resources.StringResource
    ) : UiText()
}