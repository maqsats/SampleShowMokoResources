package com.dna.payments.kmm.presentation.ui.features.login

import cafe.adriel.voyager.core.model.coroutineScope
import com.dna.payments.kmm.data.preferences.Preferences
import com.dna.payments.kmm.domain.interactors.use_cases.authorization.AuthorizationUseCase
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authorizationUseCase: AuthorizationUseCase,
    private val preference: Preferences
) : BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {

    init {
        coroutineScope.launch {
            println(authorizationUseCase("maksat.inkar@optomany.com", "ModelA1586#"))
            println(preference.getSectionAccessLevel())
        }
    }

    override fun createInitialState(): LoginContract.State =
        LoginContract.State(
            email = "",
            password = ""
        )

    override fun handleEvent(event: LoginContract.Event) {

    }

    fun onLoginClicked(email: String, password: String) {
        setState {
            copy(
                email = email,
                password = password
            )
        }
    }
}
