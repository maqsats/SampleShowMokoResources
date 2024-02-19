package com.dnapayments.mp.utils.navigation

import androidx.compose.runtime.Composable

@Composable
internal actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) =
    androidx.activity.compose.BackHandler(enabled, onBack)

actual val shouldUseSwipeBack: Boolean = false