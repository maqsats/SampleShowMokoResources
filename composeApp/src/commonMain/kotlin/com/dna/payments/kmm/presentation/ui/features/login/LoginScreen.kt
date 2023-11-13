package com.dna.payments.kmm.presentation.ui.features.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
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

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val loginViewModel = getScreenModel<LoginViewModel>()

        val state by loginViewModel.uiState.collectAsState()


        val openDialog = remember { mutableStateOf(false) }
        var loadingText by remember { mutableStateOf("") }

        LaunchedEffect(state.authorization) {
            when (state.authorization) {
                is ResourceUiState.Success -> {
                    println("Success")
                    loadingText = "Success"

                }

                is ResourceUiState.Error -> {
                    println("Error")
                    loadingText = "Error"

                }

                is ResourceUiState.Loading -> {
                    openDialog.value = true
                    loadingText = "Loading"
                    println("Loading")
                }

                is ResourceUiState.Idle -> {
                    println("Idle")
                }

                ResourceUiState.Empty -> {
                    println("Empty")
                    loadingText = "Empty"
                }
            }
        }
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onDismissRequest.
                    openDialog.value = false
                },
                properties = DialogProperties(
                    dismissOnClickOutside = false,
                )
            ) {
                Box(
                    modifier = Modifier.size(100.dp)
                        .background(shape = RoundedCornerShape(8.dp), color = Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    DNAText(loadingText, style = DnaTextStyle.Bold20)
                }
            }
        }

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
                    onLoginClicked = { loginViewModel.setEvent(LoginContract.Event.OnLoginClicked) },
                    onFieldChanged = { loginViewModel.setEvent(LoginContract.Event.OnFieldChanged) },
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
        onLoginClicked: () -> Unit,
        onFieldChanged: () -> Unit,
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
                onFieldChanged = onFieldChanged
            )
            Spacer(modifier = Modifier.height(24.dp))
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
        Button(
            onClick = onLoginClicked,
            enabled = state.isLoginEnabled.value,
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
        email: TextFieldUiState,
        onFieldChanged: () -> Unit,
        password: TextFieldUiState,

        ) {
        EmailField(
            email = email,
            onFieldChanged
        )
        Spacer(modifier = Modifier.height(32.dp))
        PasswordField(
            password = password,
            onFieldChanged
        )
    }

    @Composable
    private fun PasswordField(
        password: TextFieldUiState,
        onPasswordChanged: () -> Unit,
    ) {
        DNAText(
            text = stringResource(MR.strings.password),
            style = DnaTextStyle.Medium16
        )
        Spacer(modifier = Modifier.height(8.dp))
        DNAPasswordTextField(password, onPasswordChanged)
    }

    @Composable
    private fun EmailField(
        email: TextFieldUiState,
        onEmailChanged: () -> Unit,
    ) {
        DNAText(
            text = stringResource(MR.strings.email),
            style = DnaTextStyle.Medium16,
        )
        Spacer(modifier = Modifier.height(8.dp))
        DNAEmailTextField(email, onEmailChanged)
    }
}