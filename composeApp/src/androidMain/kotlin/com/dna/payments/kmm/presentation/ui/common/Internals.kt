package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.runtime.Composable

@Composable
internal actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) =
    androidx.activity.compose.BackHandler(enabled, onBack)

actual val shouldUseSwipeBack: Boolean = false