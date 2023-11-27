package com.dna.payments.kmm.utils.navigation.drawer_navigation

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey

interface DrawerScreen : Screen {
    override val key: ScreenKey
        get() = uniqueScreenKey

    val isFilterEnabled: Boolean
        get() = true

    @Composable
    override fun Content() {
        DrawerContent()
    }

    @Composable
    fun DrawerContent()

    @Composable
    fun DrawerHeader()

    @Composable
    fun DrawerFilter()
}
