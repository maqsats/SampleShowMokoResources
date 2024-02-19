package com.dnapayments.mp.utils.navigation.drawer_navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.platform.multiplatformName
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey

interface DrawerScreen : Screen {
    override val key: ScreenKey
        get() = commonKeyGeneration()

    val isFilterEnabled: Boolean
        get() = true

    @Composable
    override fun Content()

    @Composable
    fun DrawerContent(isToolbarCollapsed: Boolean) {
        Content()
    }

    @Composable
    fun DrawerHeader()

    @Composable
    fun DrawerFilter()
}

internal fun DrawerScreen.commonKeyGeneration() =
    this::class.multiplatformName
        ?: error("Default ScreenKey not found, please provide your own key")
