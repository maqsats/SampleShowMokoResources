package com.dna.payments.kmm.presentation.ui.features.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.ui.common.DNAEmailTextField
import com.dna.payments.kmm.presentation.ui.common.DNAPasswordTextField
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNAYellowButton
import com.dna.payments.kmm.presentation.ui.common.UiStateController
import com.dna.payments.kmm.presentation.ui.features.forgot_password.ForgotPasswordScreen
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest

class LoginScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val loginViewModel = getScreenModel<LoginViewModel>()

        val state by loginViewModel.uiState.collectAsState()

        val controller = LocalSoftwareKeyboardController.current
        val navigator = LocalNavigator.currentOrThrow

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
            LoginContent(
                modifier = Modifier.wrapContentHeight(),
                state = state,
                onLoginClicked = { loginViewModel.setEvent(LoginContract.Event.OnLoginClicked) },
                onForgotPasswordClicked = {
                    navigator.push(ForgotPasswordScreen())
                }
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }
    }

    @Composable
    private fun LoginContent(
        modifier: Modifier = Modifier,
        state: LoginContract.State,
        onLoginClicked: () -> Unit,
        onForgotPasswordClicked: () -> Unit
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            DNAText(
                text = stringResource(MR.strings.login),
                style = DnaTextStyle.SemiBold20
            )
            Spacer(modifier = Modifier.height(32.dp))
            LoginFields(
                email = state.email,
                password = state.password
            )
            Spacer(modifier = Modifier.height(12.dp))
            ForgotPasswordButton(
                onForgotPasswordClicked = onForgotPasswordClicked
            )
            Spacer(modifier = Modifier.height(32.dp))
            LoginButton(
                onLoginClicked = onLoginClicked,
                state
            )
        }
    }

    @Composable
    private fun ForgotPasswordButton(onForgotPasswordClicked: () -> Unit) {
        DNAText(
            text = stringResource(MR.strings.restore_password),
            style = DnaTextStyle.Green16,
            modifier = Modifier.fillMaxWidth().noRippleClickable { onForgotPasswordClicked() }
        )
    }

    @Composable
    private fun LoginButton(onLoginClicked: () -> Unit, state: LoginContract.State) {
        DNAYellowButton(
            text = stringResource(MR.strings.login),
            onClick = onLoginClicked,
            enabled = state.isLoginEnabled.value
        )
    }

    @Composable
    private fun LoginFields(
        email: TextFieldUiState,
        password: TextFieldUiState,

        ) {
        EmailField(
            email = email
        )
        Spacer(modifier = Modifier.height(32.dp))
        PasswordField(
            password = password
        )
    }

    @Composable
    private fun PasswordField(
        password: TextFieldUiState
    ) {
        DNAText(
            text = stringResource(MR.strings.password),
            style = DnaTextStyle.Medium16
        )
        Spacer(modifier = Modifier.height(8.dp))
        DNAPasswordTextField(password)
    }

    @Composable
    private fun EmailField(
        email: TextFieldUiState
    ) {
        DNAText(
            text = stringResource(MR.strings.email),
            style = DnaTextStyle.Medium16,
        )
        Spacer(modifier = Modifier.height(8.dp))
        DNAEmailTextField(email)
    }
}