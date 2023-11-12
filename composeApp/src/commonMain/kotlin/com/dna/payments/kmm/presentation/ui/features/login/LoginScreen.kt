package com.dna.payments.kmm.presentation.ui.features.login

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dna.payments.kmm.MR
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.theme.backgroundBtnNotEnabled
import com.dna.payments.kmm.presentation.theme.black
import com.dna.payments.kmm.presentation.theme.greyColor
import com.dna.payments.kmm.presentation.theme.white
import com.dna.payments.kmm.presentation.theme.yellowButton
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
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
            Text(
                text = "Login",
                style = DnaTextStyle.SemiBold20
            )
            Spacer(modifier = Modifier.height(32.dp))
            LoginFields(
                email = state.email,
                password = state.password,
                onEmailChanged = { onLoginClicked },
                onPasswordChanged = { onLoginClicked }
            )
            Spacer(modifier = Modifier.height(32.dp))
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
        Text(
            text = "Restore password",
            style = DnaTextStyle.Green16,
            modifier = Modifier.fillMaxWidth().clickable { onForgotPasswordClicked() }
        )
    }

    private @Composable
    fun LoginButton(state: LoginContract.State, onLoginClicked: () -> Any) {
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
            Text(
                text = "Log In",
                style = DnaTextStyle.SemiBold18,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }

    @Composable
    private fun LoginFields(
        email: Any,
        password: Any,
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
        Text(
            text = "Password",
            style = DnaTextStyle.Medium16
        )
        Spacer(modifier = Modifier.height(8.dp))
        PasswordInput(
            password = password,
            onPasswordChanged = onPasswordChanged
        )
    }

    @Composable
    private fun PasswordInput(password: Any, onPasswordChanged: () -> Any) {
        Column {
            var textState by remember { mutableStateOf("") }
            var passwordVisibility by remember { mutableStateOf(false) }
            val lightBlue = white
            TextField(
                modifier = Modifier.fillMaxWidth()
                    .border(1.dp, greyColor, shape = RoundedCornerShape(10.dp)),
                value = textState,
                placeholder = { Text("Password", style = DnaTextStyle.WithAlpha16) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = lightBlue,
                    cursorColor = greyColor,
                    disabledLabelColor = lightBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                onValueChange = {
                    textState = it
                },
                singleLine = true,
                trailingIcon = {
                    Icon(
                        painter = painterResource(MR.images.ic_visibility),
                        contentDescription = null,
                        modifier = Modifier.clickable(
                            indication = null,
                            interactionSource = MutableInteractionSource(),
                            onClick = { passwordVisibility = !passwordVisibility }
                        )
                    )
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
            )
        }
    }

    @Composable
    private fun EmailField(email: Any, onEmailChanged: () -> Any) {
        Text(
            text = "Email address",
            style = DnaTextStyle.Medium16
        )
        Spacer(modifier = Modifier.height(8.dp))
        EmailInput(
            email = email,
            onEmailChanged = onEmailChanged
        )
    }

    @Composable
    private fun EmailInput(email: Any, onEmailChanged: () -> Any) {
        Column {
            var textState by remember { mutableStateOf("") }
            val lightBlue = white
            val blue = greyColor
            TextField(
                modifier = Modifier.fillMaxWidth()
                    .border(1.dp, greyColor, shape = RoundedCornerShape(10.dp)),
                value = textState,
                placeholder = { Text("Email address", style = DnaTextStyle.WithAlpha16) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = lightBlue,
                    cursorColor = greyColor,
                    disabledLabelColor = lightBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                onValueChange = {
                    textState = it
                },
                singleLine = true,
                trailingIcon = {
                    if (textState.isNotEmpty()) {
                        IconButton(onClick = { textState = "" }) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    }
}


