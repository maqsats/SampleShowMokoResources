package com.dna.payments.kmm.presentation.ui.features.pincode

import cafe.adriel.voyager.core.model.screenModelScope
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
        setState {
            copy(
                pinState = if (pinUseCase.isPinCodeExist()) PinState.ENTER else PinState.CREATE,
                showBiometric = pinUseCase.isPinCodeExist()
            )
        }
    }

    override fun createInitialState(): PinContract.State = getInitialState()

    private fun getInitialState() = PinContract.State(
        codePin = Code.Pin(),
        pinState = PinState.ENTER,
        getAccessToken = ResourceUiState.Idle,
        showBiometric = false
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
            PinContract.Event.OnDispose -> {
                setState { getInitialState() }
            }
        }
    }

    private fun showBiometric() {
        screenModelScope.launch {
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

    private fun onEraseClicked() {
        when {
            repeatString.isNotEmpty() -> {
                repeatString = repeatString.dropLast(1)
                if (repeatString.isEmpty()) {
                    setState {
                        copy(
                            pinState = PinState.ENTER,
                            codePin = Code.Pin(input = pinString)
                        )
                    }
                    return
                }
                setState {
                    copy(
                        codePin = Code.Pin(input = repeatString)
                    )
                }
            }
            pinString.isNotEmpty() -> {
                pinString = pinString.dropLast(1)
                setState {
                    copy(
                        pinState = PinState.ENTER,
                        codePin = Code.Pin(input = pinString)
                    )
                }
            }
        }
    }

    private fun onNumberEntered(numPad: String) = with(currentState) {
        if (pinState == PinState.SUCCESS || pinState == PinState.ERROR) return@with
        when {
            pinString.length == 4 && repeatString.length < 4 -> {
                repeatString += numPad
                setState {
                    copy(
                        codePin = Code.Pin(input = repeatString)
                    )
                }

                if (repeatString.length == 4) {
                    if (repeatString == pinString) {
                        pinUseCase.savePin(pinString)
                        setState { copy(pinState = PinState.SUCCESS) }
                        setEffect { PinContract.Effect.OnPinCorrect }
                    } else {
                        screenModelScope.launch {
                            setState {
                                copy(
                                    pinState = PinState.ERROR,
                                    codePin = Code.Pin(isCorrect = false, input = repeatString)
                                )
                            }
                            delay(1000)
                            pinString = ""
                            repeatString = ""
                            setState {
                                copy(
                                    pinState = PinState.ENTER,
                                    codePin = Code.Pin(input = pinString)
                                )
                            }
                        }
                    }
                }
            }
            pinString.length < 4 -> {
                pinString += numPad
                setState {
                    copy(
                        codePin = Code.Pin(input = pinString)
                    )
                }
                if (pinString.length == 4) {
                    if (pinUseCase.isPinCodeExist()) {
                        if (pinUseCase.isPinCorrect(pinString)) {
                            getAccessToken()
                        } else {
                            screenModelScope.launch {
                                pinString = ""
                                setState {
                                    copy(
                                        pinState = PinState.ERROR_REPEAT,
                                        codePin = Code.Pin(isCorrect = false)
                                    )
                                }
                                delay(1000)
                                pinString = ""
                                repeatString = ""
                                setState {
                                    copy(
                                        pinState = PinState.ENTER,
                                        codePin = Code.Pin(input = pinString)
                                    )
                                }
                            }
                        }
                        return
                    }
                    setState {
                        copy(
                            pinState = PinState.REPEAT,
                            codePin = Code.Pin(input = repeatString)
                        )
                    }
                }
            }
        }
    }

    private fun onResetPin() = with(currentState) {
        pinString = ""
        repeatString = ""
        setState {
            copy(
                codePin = Code.Pin(input = pinString)
            )
        }
    }

    private fun getAccessToken() {
        setState { copy(getAccessToken = ResourceUiState.Loading) }
        screenModelScope.launch {
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