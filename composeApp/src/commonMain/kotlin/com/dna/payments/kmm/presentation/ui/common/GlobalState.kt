package com.dna.payments.kmm.presentation.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf

data class GlobalState(
    var sheetContent: @Composable () -> Unit = {},
    var isBottomSheetShow: MutableState<Boolean> = mutableStateOf(false)
)

internal val LocalTest = compositionLocalOf { false }

data class BottomSheetState(
    var isVisible: Boolean = false,
    var sheetContent: @Composable () -> Unit = {}
)

val LocalBottomSheetState = compositionLocalOf {
    BottomSheetState()
}