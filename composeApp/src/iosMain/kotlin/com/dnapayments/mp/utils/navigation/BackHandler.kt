package com.dnapayments.mp.utils.navigation

import androidx.compose.runtime.Composable

@Composable
internal actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) = Unit

actual val shouldUseSwipeBack: Boolean = true