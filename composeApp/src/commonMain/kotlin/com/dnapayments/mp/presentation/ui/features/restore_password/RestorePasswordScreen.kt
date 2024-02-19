package com.dnapayments.mp.presentation.ui.features.restore_password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.main_screens.ScreenName
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.ui.common.DNAEmailTextField
import com.dnapayments.mp.presentation.ui.common.DNAGreenBackButton
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.presentation.ui.common.DNAYellowButton
import com.dnapayments.mp.presentation.ui.common.UiStateController
import com.dnapayments.mp.presentation.ui.features.verification_code.VerificationCodeScreen
import com.dnapayments.mp.utils.constants.Constants
import com.dnapayments.mp.utils.extension.noRippleClickable
import com.dnapayments.mp.utils.firebase.logEvent
import com.dnapayments.mp.utils.navigation.LocalNavigator
import com.dnapayments.mp.utils.navigation.currentOrThrow
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest

class RestorePasswordScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val restorePasswordViewModel = getScreenModel<RestorePasswordViewModel>()

        val state by restorePasswordViewModel.uiState.collectAsState()

        val controller = LocalSoftwareKeyboardController.current
        val navigator = LocalNavigator.currentOrThrow

        LifecycleEffect(
            onStarted = {
                logEvent(
                    Constants.SCREEN_OPEN_EVENT,
                    mapOf(Constants.SCREEN_NAME to ScreenName.RESTORE_PASSWORD)
                )
            }
        )


        UiStateController(state.sendInstruction)

        LaunchedEffect(key1 = Unit) {
            restorePasswordViewModel.effect.collectLatest { effect ->
                when (effect) {
                    RestorePasswordContract.Effect.OnInstructionSuccess -> {
                        navigator.push(VerificationCodeScreen(state.email.input.value))
                    }
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
                    restorePasswordViewModel.setEvent(RestorePasswordContract.Event.OnButtonClicked)
                }
            )
            Spacer(modifier = Modifier.weight(0.5f))
        }
    }

    @Composable
    private fun RestorePasswordContent(
        modifier: Modifier = Modifier,
        state: RestorePasswordContract.State,
        onBackToLoginClicked: () -> Unit,
        onSendClicked: () -> Unit
    ) {
        Column(
            modifier = modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            DNAGreenBackButton(
                text = stringResource(MR.strings.back_to_login),
                onClick = onBackToLoginClicked,
                modifier = Modifier.padding(horizontal = 0.dp)
            )
            Spacer(modifier = modifier.height(16.dp))
            DNAText(
                text = stringResource(MR.strings.reset_password),
                style = DnaTextStyle.Bold20
            )
            Spacer(modifier = modifier.height(8.dp))
            DNAText(
                text = stringResource(MR.strings.reset_password_description),
                style = DnaTextStyle.Normal14
            )
            Spacer(modifier = modifier.height(32.dp))
            DNAText(
                text = stringResource(MR.strings.email),
                style = DnaTextStyle.Medium16,
            )
            Spacer(modifier = Modifier.height(8.dp))
            DNAEmailTextField(state.email)
            Spacer(modifier = modifier.height(32.dp))
            DNAYellowButton(
                text = stringResource(MR.strings.send_instructions),
                onClick = onSendClicked,
                screenName = ScreenName.RESTORE_PASSWORD,
                enabled = state.isButtonEnabled.value
            )
        }
    }
}