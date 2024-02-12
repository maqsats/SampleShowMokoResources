package com.dna.payments.kmm.presentation.ui.features.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.domain.model.main_screens.ScreenName
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.ui.common.DNAEmailTextField
import com.dna.payments.kmm.presentation.ui.common.DNAPasswordTextField
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNAYellowButton
import com.dna.payments.kmm.presentation.ui.common.SuccessPopup
import com.dna.payments.kmm.presentation.ui.common.UiStateController
import com.dna.payments.kmm.presentation.ui.features.pincode.PinScreen
import com.dna.payments.kmm.presentation.ui.features.restore_password.RestorePasswordScreen
import com.dna.payments.kmm.utils.UiText
import com.dna.payments.kmm.utils.constants.Constants
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.firebase.logEvent
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

class LoginScreen(private var showSuccess: Boolean = false) : Screen {
    override val key: ScreenKey = ScreenName.LOGIN.name

    @Composable
    override fun Content() {
        val loginViewModel = getScreenModel<LoginViewModel>()

        val state by loginViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            loginViewModel.effect.collect { effect ->
                when (effect) {
                    LoginContract.Effect.OnLoginSuccess -> {
                        navigator.push(PinScreen())
                    }
                }
            }
        }

        LifecycleEffect(
            onStarted = {
                logEvent(
                    Constants.SCREEN_OPEN_EVENT,
                    mapOf(Constants.SCREEN_NAME to ScreenName.LOGIN)
                )
            }
        )

        val controller = LocalSoftwareKeyboardController.current

        val showSuccessChangePassword = mutableStateOf(showSuccess)

        SuccessPopup(
            UiText.StringResource(MR.strings.success_new_password),
            showSuccessChangePassword
        )

        UiStateController(state.authorization)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .noRippleClickable {
                    controller?.hide()
                }.background(Color.White),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.weight(0.2f))
            LoginContent(
                modifier = Modifier.wrapContentHeight(),
                state = state,
                onLoginClicked = { loginViewModel.setEvent(LoginContract.Event.OnLoginClicked) },
                onForgotPasswordClicked = {
                    navigator.push(RestorePasswordScreen())
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                DNAText(
                    text = stringResource(MR.strings.login),
                    style = DnaTextStyle.SemiBold20
                )
                Icon(
                    painterResource(MR.images.ic_logo_auth),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
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
            style = DnaTextStyle.GreenMedium16,
            modifier = Modifier.wrapContentWidth().noRippleClickable { onForgotPasswordClicked() }
        )
    }

    @Composable
    private fun LoginButton(onLoginClicked: () -> Unit, state: LoginContract.State) {
        DNAYellowButton(
            text = stringResource(MR.strings.login),
            onClick = onLoginClicked,
            enabled = state.isLoginEnabled.value,
            screenName = ScreenName.LOGIN
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