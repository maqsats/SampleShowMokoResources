package com.dnapayments.mp.presentation.ui.features.nav_auth

import androidx.compose.runtime.mutableStateOf
import com.dnapayments.mp.domain.interactors.use_cases.pincode.PinUseCase
import com.dnapayments.mp.domain.model.nav_auth.NavAuth
import com.dnapayments.mp.presentation.mvi.BaseViewModel

class NavAuthViewModel(pinUseCase: PinUseCase) : BaseViewModel<
        NavAuthContract.Event, NavAuthContract.State, NavAuthContract.Effect>() {

    init {
        currentState.navAuth.value = if (pinUseCase.isPinCodeExist()) NavAuth.PIN else NavAuth.LOGIN
    }

    override fun createInitialState(): NavAuthContract.State {
        return NavAuthContract.State(
            navAuth = mutableStateOf(NavAuth.LOGIN)
        )
    }

    override fun handleEvent(event: NavAuthContract.Event) {

    }
}