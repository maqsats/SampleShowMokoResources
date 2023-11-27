package com.dna.payments.kmm.presentation.ui.features.pincode

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.coroutineScope
import com.dna.payments.kmm.domain.interactors.use_cases.authorization.AuthorizationUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.pincode.PinUseCase
import com.dna.payments.kmm.domain.model.pincode.Code
import com.dna.payments.kmm.domain.model.pincode.PinState
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import com.dna.payments.kmm.utils.UiText
import com.dna.payments.kmm.utils.biometry.BiometryAuthenticator
import dev.icerock.moko.resources.desc.desc
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PinViewModel(
    private val pinUseCase: PinUseCase,
    private val authorizationUseCase: AuthorizationUseCase,
    val biometryAuthenticator: BiometryAuthenticator,
    private val title: String,
    private val requestReason: String,
    private val failure: String
) :
    BaseViewModel<PinContract.Event, PinContract.State, PinContract.Effect>() {

    private var pinString = ""
    private var repeatString = ""

    init {
        currentState.pinState.value =
            if (pinUseCase.isPinCodeExist()) PinState.ENTER else PinState.CREATE
        currentState.showBiometric.value = pinUseCase.isPinCodeExist()
    }

    override fun createInitialState(): PinContract.State = PinContract.State(
        codePin = mutableStateOf(Code.Pin()),
        pinState = mutableStateOf(
            PinState.ENTER
        ),
        getAccessToken = ResourceUiState.Idle,
        showBiometric = mutableStateOf(false)
    )

    override fun handleEvent(event: PinContract.Event) {
        when (event) {
            is PinContract.Event.OnDigit -> {
                onNumberEntered(event.digit)
            }
            is PinContract.Event.OnErase -> {
                onEraseClicked()
            }
            is PinContract.Event.OnLogout -> {
                onLogout()
            }
            PinContract.Event.OnBiometricClick -> {
                showBiometric()
            }
        }
    }

    private fun showBiometric() {
        coroutineScope.launch {
            try {
                val isSuccess = biometryAuthenticator.checkBiometryAuthentication(
                    requestTitle = title.desc(),
                    requestReason = requestReason.desc(),
                    failureButtonText = failure.desc(),
                    allowDeviceCredentials = false
                )
                if (isSuccess) {
                    getAccessToken()
                }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            }
        }
    }

    private fun onLogout() {
        pinUseCase.resetPin()
        setEffect {
            PinContract.Effect.OnLogout
        }
    }

    private fun onEraseClicked() = with(currentState) {
        when {
            repeatString.isNotEmpty() -> {
                repeatString = repeatString.dropLast(1)
                if (repeatString.isEmpty()) {
                    pinState.value = PinState.ENTER
                    codePin.value = Code.Pin(input = pinString)
                    return
                }
                codePin.value = Code.Pin(input = repeatString)
            }
            pinString.isNotEmpty() -> {
                pinString = pinString.dropLast(1)
                codePin.value = Code.Pin(input = pinString)
                pinState.value = PinState.ENTER
            }
        }
    }

    private fun onNumberEntered(numPad: String) = with(currentState) {
        if (pinState.value == PinState.SUCCESS || pinState.value == PinState.ERROR) return@with
        when {
            pinString.length == 4 && repeatString.length < 4 -> {
                repeatString += numPad
                codePin.value = Code.Pin(input = repeatString)

                if (repeatString.length == 4) {
                    if (repeatString == pinString) {
                        pinUseCase.savePin(pinString)
                        pinState.value = PinState.SUCCESS
                        setEffect { PinContract.Effect.OnPinCorrect }
                    } else {
                        coroutineScope.launch {
                            codePin.value = Code.Pin(isCorrect = false, input = pinString)
                            pinState.value = PinState.ERROR
                            delay(1000)
                            pinString = ""
                            repeatString = ""
                            pinState.value = PinState.ENTER
                            codePin.value = Code.Pin(input = pinString)
                        }
                    }
                }
            }
            pinString.length < 4 -> {
                pinString += numPad
                codePin.value = Code.Pin(input = pinString)
                if (pinString.length == 4) {
                    if (pinUseCase.isPinCodeExist()) {
                        if (pinUseCase.isPinCorrect(pinString)) {
                            getAccessToken()
                        } else {
                            coroutineScope.launch {
                                pinString = ""
                                codePin.value = Code.Pin(isCorrect = false)
                                pinState.value = PinState.ERROR_REPEAT
                                delay(1000)
                                pinString = ""
                                repeatString = ""
                                pinState.value = PinState.ENTER
                                codePin.value = Code.Pin(input = pinString)
                            }
                        }
                        return
                    }
                    codePin.value = Code.Pin(input = repeatString)
                    pinState.value = PinState.REPEAT
                }
            }
        }
    }

    private fun onResetPin() = with(currentState) {
        pinString = ""
        repeatString = ""
        codePin.value = Code.Pin(input = pinString)
    }

    private fun getAccessToken() {
        setState { copy(getAccessToken = ResourceUiState.Loading) }
        coroutineScope.launch {
            val result = authorizationUseCase.updateToken()
            setState {
                copy(
                    getAccessToken = when (result) {
                        is Response.Success -> {
                            setEffect { PinContract.Effect.OnPinCorrect }
                            ResourceUiState.Success(result.data)
                        }
                        is Response.Error -> {
                            onResetPin()
                            ResourceUiState.Error(result.error)
                        }
                        is Response.NetworkError -> {
                            onResetPin()
                            ResourceUiState.Error(UiText.DynamicString("Network error"))
                        }
                        is Response.TokenExpire -> {
                            onResetPin()
                            ResourceUiState.Error(UiText.DynamicString("Token expired"))
                        }
                    }
                )
            }
        }
    }

    companion object {
        const val PinSize = 4
    }
}