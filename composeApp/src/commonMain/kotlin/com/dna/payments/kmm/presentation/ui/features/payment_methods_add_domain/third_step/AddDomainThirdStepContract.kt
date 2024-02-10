package com.dna.payments.kmm.presentation.ui.features.payment_methods_add_domain.third_step

import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState

interface AddDomainThirdStepContract {
    sealed interface Event : UiEvent {
        data class OnAddNewDomain(val domain: String, val paymentMethodType: PaymentMethodType) : Event
    }

    data class State(
        val addDomainState: ResourceUiState<Unit>
    ) : UiState

    sealed interface Effect : UiEffect {
        data object OnRegisterNewDomainSuccess : Effect
    }
}


