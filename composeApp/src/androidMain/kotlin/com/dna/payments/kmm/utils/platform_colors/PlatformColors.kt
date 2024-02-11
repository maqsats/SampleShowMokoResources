package com.dna.payments.kmm.utils.platform_colors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
actual fun PlatformColors(statusBarColor: Color, navBarColor: Color) {
    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(color = if (statusBarColor == Color.Transparent) Color.White else statusBarColor)
        sysUiController.setNavigationBarColor(color = navBarColor)
    }
}