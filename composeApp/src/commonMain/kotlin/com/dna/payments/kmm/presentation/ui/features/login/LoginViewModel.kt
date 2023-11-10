package com.dna.payments.kmm.presentation.ui.features.login

import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.BaseViewModel

class LoginViewModel(
) : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    init {
//        getCharacters()
    }

    override fun createInitialState(): LoginContract.State =
        LoginContract.State(characters = ResourceUiState.Idle)

    override fun handleEvent(event: LoginContract.Event) {

    }
}
