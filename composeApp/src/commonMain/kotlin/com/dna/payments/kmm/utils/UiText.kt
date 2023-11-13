package com.dna.payments.kmm.utils

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.compose.stringResource

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class StringResource(
        val stringResource: dev.icerock.moko.resources.StringResource
    ) : UiText()

    @Composable
    fun getText(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(stringResource)
        }
    }
}