package com.dna.payments.kmm.presentation.ui.features.new_password

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
import com.dna.payments.kmm.utils.extension.noRippleClickable
import com.dna.payments.kmm.utils.navigation.LocalNavigator
import com.dna.payments.kmm.utils.navigation.currentOrThrow
import kotlinx.coroutines.flow.collectLatest

class NewPasswordScreen(id: String) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val restorePasswordViewModel = getScreenModel<NewPasswordViewModel>()

        val state by restorePasswordViewModel.uiState.collectAsState()

        val controller = LocalSoftwareKeyboardController.current
        val navigator = LocalNavigator.currentOrThrow


        UiStateController(state.resetPassword)

        LaunchedEffect(key1 = Unit) {
            restorePasswordViewModel.effect.collectLatest { effect ->
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
                    restorePasswordViewModel.setEvent(NewPasswordContract.Event.OnButtonClicked)
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
    }
}