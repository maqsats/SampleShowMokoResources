package com.dnapayments.mp.presentation.ui.features.nav_auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.dnapayments.mp.domain.model.nav_auth.NavAuth
import com.dnapayments.mp.presentation.ui.features.login.LoginScreen
import com.dnapayments.mp.presentation.ui.features.pincode.PinScreen

class NavAuthScreen : Screen {

    @Composable
    override fun Content() {
        val navAuthViewModel = getScreenModel<NavAuthViewModel>()

        val state = navAuthViewModel.uiState.value

        when (state.navAuth.value) {
            NavAuth.LOGIN -> {
                LoginScreen().Content()
            }
            NavAuth.PIN -> {
                PinScreen().Content()
            }
        }
    }
}