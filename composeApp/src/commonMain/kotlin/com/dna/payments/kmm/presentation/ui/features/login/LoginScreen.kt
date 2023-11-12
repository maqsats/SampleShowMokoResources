package com.dna.payments.kmm.presentation.ui.features.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.backgroundBtnNotEnabled
import com.dna.payments.kmm.presentation.theme.black
import com.dna.payments.kmm.presentation.theme.yellowButton
import com.dna.payments.kmm.presentation.ui.common.DNAEmailTextField
import com.dna.payments.kmm.presentation.ui.common.DNAPasswordTextField
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest

class LoginScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val loginViewModel = getScreenModel<LoginViewModel>()

        val state by loginViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            loginViewModel.effect.collectLatest { effect ->
            }
        }

        Scaffold { padding ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(64.dp))
                LoginContent(
                    state = state,
                    onLoginClicked = { email, password ->
                        loginViewModel.onLoginClicked(email, password)
                    },
                    onForgotPasswordClicked = {
//                        navigator.push(ForgotPasswordScreen())
                    }
                )
            }
        }
    }

    @Composable
    private fun LoginContent(
        state: LoginContract.State,
        onLoginClicked: (email: String, password: String) -> Unit = { _, _ -> },
        onForgotPasswordClicked: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                password = state.password,
                onEmailChanged = { onLoginClicked },
                onPasswordChanged = { onLoginClicked }
            )
            Spacer(modifier = Modifier.height(24.dp))
            ForgotPasswordButton(
                onForgotPasswordClicked = onForgotPasswordClicked
            )
            Spacer(modifier = Modifier.height(32.dp))
            LoginButton(
                state = state,
                onLoginClicked = { onLoginClicked }
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
    private fun LoginButton(state: LoginContract.State, onLoginClicked: () -> Any) {
        Button(
            onClick = { onLoginClicked() },
            enabled = true,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = yellowButton,
                contentColor = black,
                disabledBackgroundColor = backgroundBtnNotEnabled
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            DNAText(
                text = stringResource(MR.strings.login),
                style = DnaTextStyle.SemiBold18,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }

    @Composable
    private fun LoginFields(
        email: String,
        password: String,
        onEmailChanged: () -> Any,
        onPasswordChanged: () -> Any
    ) {
        EmailField(
            email = email,
            onEmailChanged = onEmailChanged
        )
        Spacer(modifier = Modifier.height(32.dp))
        PasswordField(
            password = password,
            onPasswordChanged = onPasswordChanged
        )
    }

    @Composable
    private fun PasswordField(password: Any, onPasswordChanged: () -> Any) {
        DNAText(
            text = stringResource(MR.strings.password),
            style = DnaTextStyle.Medium16
        )
        Spacer(modifier = Modifier.height(8.dp))
        DNAPasswordTextField()
    }

    @Composable
    private fun EmailField(email: Any, onEmailChanged: () -> Any) {
        DNAText(
            text = stringResource(MR.strings.email),
            style = DnaTextStyle.Medium16
        )
        Spacer(modifier = Modifier.height(8.dp))
        DNAEmailTextField()
    }
}