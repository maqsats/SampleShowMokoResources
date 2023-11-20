package com.dna.payments.kmm.presentation.ui.features.nav_auth

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.domain.model.nav_auth.NavAuth
import com.dna.payments.kmm.presentation.ui.features.login.LoginScreen
import com.dna.payments.kmm.presentation.ui.features.pincode.PinScreen

class NavAuthScreen : Screen {
    override val key = uniqueScreenKey

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