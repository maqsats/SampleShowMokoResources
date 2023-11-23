package com.dna.payments.kmm.presentation.ui.features.new_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.dna.payments.kmm.presentation.theme.DnaTextStyle
import com.dna.payments.kmm.presentation.ui.common.DNAGreenBackButton
import com.dna.payments.kmm.presentation.ui.common.DNAPasswordRequirement
import com.dna.payments.kmm.presentation.ui.common.DNAPasswordTextField
import com.dna.payments.kmm.presentation.ui.common.DNAText
import com.dna.payments.kmm.presentation.ui.common.DNAYellowButton
import com.dna.payments.kmm.presentation.ui.common.UiStateController
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest

class NewPasswordScreen(id: String) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val newPasswordViewModel = getScreenModel<NewPasswordViewModel>()

        val state by newPasswordViewModel.uiState.collectAsState()
        val controller = LocalSoftwareKeyboardController.current
        val navigator = LocalNavigator.currentOrThrow


        UiStateController(state.newPassword)

        LaunchedEffect(key1 = Unit) {
            newPasswordViewModel.effect.collectLatest { effect ->
                when (effect) {
                    NewPasswordContract.Effect.OnResetPasswordSuccess -> {}
                }
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
            RestorePasswordContent(
                modifier = Modifier.wrapContentHeight(),
                state = state,
                onBackToLoginClicked = { navigator.pop() },
                onSendClicked = {
                    newPasswordViewModel.setEvent(NewPasswordContract.Event.OnButtonClicked)
                }
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }
    }

    @Composable
    private fun RestorePasswordContent(
        modifier: Modifier = Modifier,
        state: NewPasswordContract.State,
        onBackToLoginClicked: () -> Unit,
        onSendClicked: () -> Unit
    ) {
        Column(
            modifier = modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            DNAGreenBackButton(
                text = stringResource(MR.strings.back_to_email),
                onClick = onBackToLoginClicked,
                modifier = Modifier.padding(horizontal = 0.dp)
            )
            Spacer(modifier = modifier.height(16.dp))
            DNAText(
                text = stringResource(MR.strings.set_password),
                style = DnaTextStyle.Bold20
            )
            Spacer(modifier = modifier.height(8.dp))
            DNAText(
                text = stringResource(
                    MR.strings.new_password_description
                ),
                style = DnaTextStyle.Normal14
            )
            Spacer(modifier = modifier.height(32.dp))
            DNAText(
                text = stringResource(MR.strings.password),
                style = DnaTextStyle.Medium16,
            )
            Spacer(modifier = Modifier.height(8.dp))
            DNAPasswordTextField(state.password)
            Spacer(modifier = modifier.height(24.dp))
            DNAText(
                text = stringResource(MR.strings.confirm_password),
                style = DnaTextStyle.Medium16,
            )
            Spacer(modifier = Modifier.height(8.dp))
            DNAPasswordTextField(state.confirmPassword)
            Spacer(modifier = modifier.height(32.dp))
            DNAYellowButton(
                text = stringResource(MR.strings.confirm),
                onClick = onSendClicked,
                enabled = state.isButtonEnabled.value
            )
            Spacer(modifier = modifier.height(24.dp))
            DNAText(
                text = stringResource(MR.strings.password_must_contain),
                style = DnaTextStyle.Medium16,
            )
            Spacer(modifier = modifier.height(16.dp))
            LazyColumn {
                items(state.validationList.value) { it ->
                    DNAPasswordRequirement(passwordRequirement = it)
                }
            }
        }
    }
}