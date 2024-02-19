package com.dnapayments.mp.utils

import androidx.compose.runtime.Composable
import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize
import dev.icerock.moko.resources.compose.stringResource

@Parcelize
sealed class UiText : Parcelable {
    @Parcelize
    data class DynamicString(val value: String) : UiText(), Parcelable

    @Parcelize
    class StringResource(
        val stringResource: dev.icerock.moko.resources.StringResource
    ) : UiText(), Parcelable

    @Composable
    fun getText(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(stringResource)
        }
    }
}