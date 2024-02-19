package com.dnapayments.mp.presentation.ui.features.drawer_navigation

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
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.date_picker.Menu
import com.dnapayments.mp.domain.model.main_screens.ScreenName
import com.dnapayments.mp.domain.model.nav_item.NavItemPosition
import com.dnapayments.mp.domain.model.nav_item.SettingsPosition
import com.dnapayments.mp.presentation.ui.common.LocalSelectedMerchant
import com.dnapayments.mp.presentation.ui.features.help_center.HelpCenterScreen
import com.dnapayments.mp.presentation.ui.features.new_payment_link.NewPaymentLinkScreen
import com.dnapayments.mp.presentation.ui.features.online_payments.OnlinePaymentsScreen
import com.dnapayments.mp.presentation.ui.features.overview_report.OverviewReportScreen
import com.dnapayments.mp.presentation.ui.features.payment_links.PaymentLinksScreen
import com.dnapayments.mp.presentation.ui.features.payment_methods.PaymentMethodsScreen
import com.dnapayments.mp.presentation.ui.features.pos_payments.PosPaymentsScreen
import com.dnapayments.mp.presentation.ui.features.team_management.TeamManagementScreen
import com.dnapayments.mp.utils.constants.Constants.NAV_ITEM
import com.dnapayments.mp.utils.constants.Constants.SCREEN_NAME
import com.dnapayments.mp.utils.constants.Constants.SCREEN_OPEN_EVENT
import com.dnapayments.mp.utils.firebase.logEvent
import com.dnapayments.mp.utils.navigation.LocalNavigator
import com.dnapayments.mp.utils.navigation.NavigatorDisposeBehavior
import com.dnapayments.mp.utils.navigation.currentOrThrow
import com.dnapayments.mp.utils.navigation.drawer_navigation.CurrentDrawerScreen
import com.dnapayments.mp.utils.navigation.drawer_navigation.LocalDrawerNavigator
import com.dnapayments.mp.utils.navigation.drawer_navigation.NavigatorContent
import com.dnapayments.mp.utils.navigation.drawer_navigation.compositionUniqueId
import com.dnapayments.mp.utils.navigation.internal.ChildrenNavigationDisposableEffect
import com.dnapayments.mp.utils.navigation.internal.DrawerStepDisposableEffect
import com.dnapayments.mp.utils.navigation.internal.LocalDrawerNavigatorStateHolder
import com.dnapayments.mp.utils.navigation.internal.rememberDrawerNavigator
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DrawerContainerScreen : Screen {

    @Composable
    override fun Content() {

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

        val content: NavigatorContent = { CurrentDrawerScreen(drawerState) }

        val scope = rememberCoroutineScope()

        val parentNavigator = LocalNavigator.currentOrThrow

        val loading = stringResource(MR.strings.loading)

        var merchantState by rememberSaveable { mutableStateOf(loading) }

        LifecycleEffect(
            onStarted = {
                logEvent(SCREEN_OPEN_EVENT, mapOf(SCREEN_NAME to ScreenName.MAIN_SCREEN))
            }
        )

        CompositionLocalProvider(
            LocalDrawerNavigatorStateHolder providesDefault rememberSaveableStateHolder()
        ) {
            val disposeBehavior = NavigatorDisposeBehavior(
                disposeSteps = false,
                disposeNestedNavigators = false
            )
            val navigator =
                rememberDrawerNavigator(
                    listOf(getInitialScreen()),
                    compositionUniqueId(),
                    disposeBehavior,
                    LocalDrawerNavigator.current
                )

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet(
                        drawerShape = RectangleShape,
                        drawerContainerColor = Color.White,
                        drawerTonalElevation = 0.dp,
                    ) {
                        DrawerNavigationScreen(
                            onNavItemClick = {
                                logEvent(SCREEN_OPEN_EVENT, mapOf(NAV_ITEM to it.name))
                                scope.launch {
                                    drawerState.close()
                                    delay(200)
                                    navigator.replaceAll(getScreenByNavItem(it))
                                }
                            },
                            onSettingsClick = {
                                logEvent(SCREEN_OPEN_EVENT, mapOf(SCREEN_NAME to it.name))
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
            NavItemPosition.OVERVIEW -> OverviewReportScreen(Menu.OVERVIEW)
            NavItemPosition.ONLINE_PAYMENTS -> OnlinePaymentsScreen()
            NavItemPosition.PAYMENT_METHODS -> PaymentMethodsScreen()
            NavItemPosition.TEAM_MANAGEMENT -> TeamManagementScreen()
            NavItemPosition.PAYMENT_LINKS -> PaymentLinksScreen()
            NavItemPosition.POS_PAYMENTS -> PosPaymentsScreen()
            NavItemPosition.ADD_NEW_PAYMENT_LINK -> NewPaymentLinkScreen()
            NavItemPosition.REPORTS -> OverviewReportScreen(Menu.REPORTS)
            else -> {
                getInitialScreen()
            }
        }

    private fun getScreenBySettingsItem(settingsPosition: SettingsPosition) =
        when (settingsPosition) {
            else -> {
                HelpCenterScreen()
            }
        }

    private fun getInitialScreen() = OverviewReportScreen(Menu.OVERVIEW)
}