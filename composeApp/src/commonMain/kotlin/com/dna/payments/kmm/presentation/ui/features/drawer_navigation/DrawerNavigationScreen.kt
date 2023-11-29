package com.dna.payments.kmm.presentation.ui.features.drawer_navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import com.dna.payments.kmm.domain.model.nav_item.NavItemPosition
import com.dna.payments.kmm.presentation.ui.features.online_payments.OnlinePaymentsScreen
import com.dna.payments.kmm.presentation.ui.features.overview.OverviewScreen
import com.dna.payments.kmm.utils.navigation.NavigatorDisposeBehavior
import com.dna.payments.kmm.utils.navigation.drawer_navigation.CurrentDrawerScreen
import com.dna.payments.kmm.utils.navigation.drawer_navigation.LocalDrawerNavigator
import com.dna.payments.kmm.utils.navigation.drawer_navigation.NavigatorContent
import com.dna.payments.kmm.utils.navigation.internal.ChildrenNavigationDisposableEffect
import com.dna.payments.kmm.utils.navigation.internal.DrawerNavigatorDisposableEffect
import com.dna.payments.kmm.utils.navigation.internal.DrawerStepDisposableEffect
import com.dna.payments.kmm.utils.navigation.internal.LocalDrawerNavigatorStateHolder
import com.dna.payments.kmm.utils.navigation.internal.rememberDrawerNavigator

class DrawerNavigationScreen : Screen {

    override val key = uniqueScreenKey

    @Composable
    override fun Content() {

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)

        val content: NavigatorContent = { CurrentDrawerScreen(drawerState) }

        CompositionLocalProvider(
            LocalDrawerNavigatorStateHolder providesDefault rememberSaveableStateHolder()
        ) {
            val disposeBehavior = NavigatorDisposeBehavior()
            val navigator =
                rememberDrawerNavigator(
                    listOf(OverviewScreen()),
                    key,
                    disposeBehavior,
                    LocalDrawerNavigator.current
                )

            if (navigator.parent?.disposeBehavior?.disposeNestedNavigators != false) {
                DrawerNavigatorDisposableEffect(navigator)
            }

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet(
                        drawerShape = RectangleShape,
                        drawerContainerColor = Color.White,
                        drawerTonalElevation = 0.dp,
                    ) {
                        DrawerView(
                            onNavItemClick = {
                                navigator.replace(getScreenByNavItem(it))
                            }).Content()
                    }
                },
            ) {
                CompositionLocalProvider(
                    LocalDrawerNavigator provides navigator
                ) {
                    if (disposeBehavior.disposeSteps) {
                        DrawerStepDisposableEffect(navigator)
                    }
                    content(navigator)
                }
                ChildrenNavigationDisposableEffect(navigator)
            }
        }
    }

    private fun getScreenByNavItem(navPosition: NavItemPosition) =
        when (navPosition) {
            NavItemPosition.OVERVIEW -> OverviewScreen()
            NavItemPosition.ONLINE_PAYMENTS -> OnlinePaymentsScreen()
            else -> {
                OverviewScreen()
            }
        }
}