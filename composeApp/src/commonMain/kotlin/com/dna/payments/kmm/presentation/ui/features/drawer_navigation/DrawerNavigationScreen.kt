package com.dna.payments.kmm.presentation.ui.features.drawer_navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.nav_item.NavItemPosition
import com.dna.payments.kmm.domain.model.nav_item.SettingsPosition
import com.dna.payments.kmm.presentation.ui.common.LocalSelectedMerchant
import com.dna.payments.kmm.presentation.ui.features.help_center.HelpCenterScreen
import com.dna.payments.kmm.presentation.ui.features.online_payments.OnlinePaymentsScreen
import com.dna.payments.kmm.presentation.ui.features.overview.OverviewScreen
import com.dna.payments.kmm.presentation.ui.features.payment_methods.PaymentMethodsScreen
import com.dna.payments.kmm.presentation.ui.features.team_management.TeamManagementScreen
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.NavigatorDisposeBehavior
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import com.dna.payments.kmm.utils.navigation.drawer_navigation.CurrentDrawerScreen
import com.dna.payments.kmm.utils.navigation.drawer_navigation.LocalDrawerNavigator
import com.dna.payments.kmm.utils.navigation.drawer_navigation.NavigatorContent
import com.dna.payments.kmm.utils.navigation.drawer_navigation.compositionUniqueId
import com.dna.payments.kmm.utils.navigation.internal.ChildrenNavigationDisposableEffect
import com.dna.payments.kmm.utils.navigation.internal.DrawerNavigatorDisposableEffect
import com.dna.payments.kmm.utils.navigation.internal.DrawerStepDisposableEffect
import com.dna.payments.kmm.utils.navigation.internal.LocalDrawerNavigatorStateHolder
import com.dna.payments.kmm.utils.navigation.internal.rememberDrawerNavigator
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch

class DrawerNavigationScreen : Screen {

    @Composable
    override fun Content() {

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

        val content: NavigatorContent = { CurrentDrawerScreen(drawerState) }

        val scope = rememberCoroutineScope()

        val parentNavigator = LocalNavigator.currentOrThrow

        val loading = stringResource(MR.strings.loading)

        var merchantState by rememberSaveable { mutableStateOf(loading) }

        CompositionLocalProvider(
            LocalDrawerNavigatorStateHolder providesDefault rememberSaveableStateHolder()
        ) {
            val disposeBehavior = NavigatorDisposeBehavior()
            val navigator =
                rememberDrawerNavigator(
                    listOf(getInitialScreen()),
                    compositionUniqueId(),
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
                        DrawerScreen(
                            onNavItemClick = {
                                scope.launch {
                                    navigator.replaceAll(getScreenByNavItem(it))
                                    drawerState.close()
                                }
                            },
                            onSettingsClick = {
                                scope.launch {
                                    drawerState.close()
                                    parentNavigator.push(getScreenBySettingsItem(it))
                                }
                            },
                            onMerchantChange = {
                                scope.launch {
                                    navigator.replaceAll(getInitialScreen())
                                    drawerState.close()
                                }
                            },
                            onMerchantSelected = {
                                merchantState = it
                            }
                        ).Content()
                    }
                },
            ) {
                CompositionLocalProvider(
                    LocalDrawerNavigator provides navigator,
                    LocalSelectedMerchant provides merchantState,
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
            NavItemPosition.PAYMENT_METHODS -> PaymentMethodsScreen()
            NavItemPosition.TEAM_MANAGEMENT -> TeamManagementScreen()
            else -> {
                OverviewScreen()
            }
        }

    private fun getScreenBySettingsItem(settingsPosition: SettingsPosition) =
        when (settingsPosition) {
            else -> {
                HelpCenterScreen()
            }
        }

    private fun getInitialScreen() = OverviewScreen()
}