package com.dnapayments.mp.presentation.ui.features.pincode

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.dnapayments.mp.MR
import com.dnapayments.mp.domain.model.main_screens.ScreenName
import com.dnapayments.mp.domain.model.pincode.Code
import com.dnapayments.mp.domain.model.pincode.KeyboardItem
import com.dnapayments.mp.domain.model.pincode.PinState.ERROR
import com.dnapayments.mp.domain.model.pincode.PinState.ERROR_REPEAT
import com.dnapayments.mp.domain.model.pincode.drawPinPattern
import com.dnapayments.mp.presentation.theme.DnaTextStyle
import com.dnapayments.mp.presentation.theme.gray
import com.dnapayments.mp.presentation.theme.greenButtonNotFilled
import com.dnapayments.mp.presentation.theme.red
import com.dnapayments.mp.presentation.ui.common.DNAText
import com.dnapayments.mp.presentation.ui.common.LogoutDialog
import com.dnapayments.mp.presentation.ui.common.UiStateController
import com.dnapayments.mp.presentation.ui.features.drawer_navigation.DrawerContainerScreen
import com.dnapayments.mp.presentation.ui.features.login.LoginScreen
import com.dnapayments.mp.utils.biometry.BindBiometryAuthenticatorEffect
import com.dnapayments.mp.utils.biometry.BiometryAuthenticatorFactory
import com.dnapayments.mp.utils.biometry.rememberBiometryAuthenticatorFactory
import com.dnapayments.mp.utils.constants.Constants
import com.dnapayments.mp.utils.extension.noRippleClickable
import com.dnapayments.mp.utils.firebase.logEvent
import com.dnapayments.mp.utils.navigation.LocalNavigator
import com.dnapayments.mp.utils.navigation.currentOrThrow
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.parameter.parametersOf


class PinScreen : Screen {

    @Composable
    override fun Content() {
        val title = stringResource(MR.strings.biometric_authentication)
        val requestReason =
            stringResource(MR.strings.biometric_authentication_description)

        val failure = stringResource(MR.strings.biometric_authentication_failure)

        val biometryFactory: BiometryAuthenticatorFactory = rememberBiometryAuthenticatorFactory()

        val pinViewModel = getScreenModel<PinViewModel> {
            parametersOf(
                biometryFactory.createBiometryAuthenticator(),
                title,
                requestReason,
                failure
            )
        }

        LifecycleEffect(
            onStarted = {
                logEvent(
                    Constants.SCREEN_OPEN_EVENT,
                    mapOf(Constants.SCREEN_NAME to ScreenName.PIN_CODE)
                )
                pinViewModel.setEvent(PinContract.Event.OnDispose)
            }
        )


        BindBiometryAuthenticatorEffect(pinViewModel.biometryAuthenticator)

        val state by pinViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow
        UiStateController(state.getAccessToken)

        LaunchedEffect(key1 = Unit) {
            pinViewModel.effect.collectLatest { effect ->
                when (effect) {
                    PinContract.Effect.OnLogout -> {
                        navigator.replaceAll(LoginScreen())
                    }
                    PinContract.Effect.OnPinCorrect -> {
                        navigator.replaceAll(DrawerContainerScreen())
                    }
                }
            }
        }

        PinCodeContent(
            onDigit = {
                pinViewModel.setEvent(PinContract.Event.OnDigit(it))
            },
            onErase = {
                pinViewModel.setEvent(PinContract.Event.OnErase)
            },
            onLogout = {
                pinViewModel.setEvent(PinContract.Event.OnLogout)
            },
            onBiometric = {
                pinViewModel.setEvent(PinContract.Event.OnBiometricClick)
            },
            pinContactState = state
        )
    }

    @Composable
    private fun PinCodeContent(
        onDigit: (String) -> Unit,
        onErase: () -> Unit,
        onLogout: () -> Unit,
        onBiometric: () -> Unit,
        pinContactState: PinContract.State,
    ) {
        var showLogoutDialog by remember { mutableStateOf(false) }

        val configuration = remember {
            Code.Configuration.Pin(
                colorScheme = Code.Configuration.ColorScheme(
                    filled = greenButtonNotFilled,
                    empty = gray,
                    error = red
                )
            )
        }

        if (showLogoutDialog) {
            LogoutDialog(
                onDismiss = { showLogoutDialog = false },
                onConfirm = onLogout
            )
        }

        Column(
            modifier = Modifier.fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Spacer(Modifier.height(50.dp))

            Row {
                Spacer(Modifier.weight(1f))
                Icon(
                    modifier = Modifier.size(27.dp).noRippleClickable {
                        showLogoutDialog = true
                    },
                    painter = painterResource(MR.images.logout),
                    tint = greenButtonNotFilled,
                    contentDescription = null
                )
                Spacer(Modifier.width(16.dp))
            }

            Spacer(Modifier.weight(0.2f))

            DNAText(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(pinContactState.pinState.stringResourceId),
                style = when (pinContactState.pinState) {
                    ERROR,
                    ERROR_REPEAT -> DnaTextStyle.RedBold20
                    else -> DnaTextStyle.GreenBold20
                },
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.weight(0.1f))

            CodeField(size = PinViewModel.PinSize) { position ->
                drawPinPattern(
                    position = position,
                    value = pinContactState.codePin,
                    configurationProvider = { configuration }
                )
            }

            Spacer(Modifier.height(88.dp))

            KeyboardLayout(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                onDigit = onDigit,
                onErase = onErase,
                onBiometric = onBiometric,
                pinContactState = pinContactState
            )

            Spacer(Modifier.height(30.dp))
        }
    }

    @Composable
    private fun KeyboardLayout(
        modifier: Modifier = Modifier,
        onDigit: (String) -> Unit,
        onErase: () -> Unit,
        onBiometric: () -> Unit,
        pinContactState: PinContract.State
    ) {
        val keyboardElements = remember {
            listOf(
                KeyboardItem.Digit.One,
                KeyboardItem.Digit.Two,
                KeyboardItem.Digit.Three,

                KeyboardItem.Digit.Four,
                KeyboardItem.Digit.Five,
                KeyboardItem.Digit.Six,

                KeyboardItem.Digit.Seven,
                KeyboardItem.Digit.Eight,
                KeyboardItem.Digit.Nine,

                KeyboardItem.Biometric,
                KeyboardItem.Digit.Zero,
                KeyboardItem.Erase
            )
        }

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(KEYBOARD_ROWS_NUMBER) { lineNumber ->
                Row(horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                    keyboardElements.subList(
                        lineNumber * KEYBOARD_ROW_LENGTH,
                        (lineNumber + 1) * KEYBOARD_ROW_LENGTH
                    )
                        .forEach { item: KeyboardItem ->
                            when (item) {
                                is KeyboardItem.Digit -> {
                                    KeyboardItemContent(onClick = { onDigit(item.value.toString()) }) {
                                        DNAText(
                                            text = "${item.value}",
                                            style = DnaTextStyle.Medium36,
                                        )
                                    }
                                }

                                KeyboardItem.Erase -> {
                                    KeyboardItemContent(onClick = onErase) {
                                        Icon(
                                            modifier = Modifier.size(50.dp),
                                            painter = painterResource(MR.images.backspace),
                                            contentDescription = null
                                        )
                                    }
                                }

                                KeyboardItem.Biometric -> {
                                    KeyboardItemContent(onClick = onBiometric) {
                                        if (pinContactState.showBiometric) {
                                            Icon(
                                                modifier = Modifier.size(50.dp),
                                                painter = painterResource(MR.images.face_id),
                                                contentDescription = null
                                            )
                                        } else {
                                            Unit
                                        }
                                    }
                                }
                            }
                        }
                }
            }
        }
    }

    @Composable
    private fun KeyboardItemContent(
        onClick: () -> Unit,
        content: @Composable () -> Unit,
    ) {
        Box(
            modifier = Modifier
                .clipToBounds()
                .requiredSize(80.dp).clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }

    companion object {
        private const val KEYBOARD_ROW_LENGTH = 3
        private const val KEYBOARD_ROWS_NUMBER = 4
    }
}

