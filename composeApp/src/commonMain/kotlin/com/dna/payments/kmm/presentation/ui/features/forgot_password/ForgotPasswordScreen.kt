package com.dna.payments.kmm.presentation.ui.features.forgot_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.presentation.ui.common.UiStateController
import com.dna.payments.kmm.presentation.ui.features.login.LoginContract
import com.dna.payments.kmm.presentation.ui.features.login.LoginViewModel
import com.dna.payments.kmm.utils.extension.noRippleClickable
import kotlinx.coroutines.flow.collectLatest

class ForgotPasswordScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val loginViewModel = getScreenModel<LoginViewModel>()

        val state by loginViewModel.uiState.collectAsState()

        val controller = LocalSoftwareKeyboardController.current


        UiStateController(state.authorization) {
            //navigate to other menu
        }

        LaunchedEffect(key1 = Unit) {
            loginViewModel.effect.collectLatest {

            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable {
                    controller?.hide()
                },
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.weight(0.2f))
            ForgotPasswordContent(
                modifier = Modifier.wrapContentHeight(),
                state = state,
                onLoginClicked = { loginViewModel.setEvent(LoginContract.Event.OnLoginClicked) },
                onForgotPasswordClicked = {
//                        navigator.push(ForgotPasswordScreen())
                }
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }
    }

    @Composable
    private fun ForgotPasswordContent(
        modifier: Modifier = Modifier,
        state: LoginContract.State,
        onLoginClicked: () -> Unit,
        onForgotPasswordClicked: () -> Unit
    ) {

    }
}