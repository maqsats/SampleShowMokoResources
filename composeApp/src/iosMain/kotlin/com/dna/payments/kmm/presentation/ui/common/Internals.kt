package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.runtime.Composable

@Composable
internal actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) = Unit

actual val shouldUseSwipeBack: Boolean = true