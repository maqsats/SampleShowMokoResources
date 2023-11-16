package com.dna.payments.kmm.presentation.ui.features.nav_auth

import androidx.compose.runtime.mutableStateOf
import com.dna.payments.kmm.domain.interactors.use_cases.pincode.PinUseCase
import com.dna.payments.kmm.domain.model.nav_auth.NavAuth
import com.dna.payments.kmm.presentation.mvi.BaseViewModel

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