package com.dnapayments.mp.presentation.ui.features.payment_methods_add_domain.third_step

import com.dnapayments.mp.domain.model.payment_methods.setting.PaymentMethodType
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

interface AddDomainThirdStepContract {
    sealed interface Event : UiEvent {
        data class OnAddNewDomain(val domain: String, val paymentMethodType: PaymentMethodType) :
            Event
    }

    data class State(
        val addDomainState: ResourceUiState<Unit>
    ) : UiState

    sealed interface Effect : UiEffect {
        data object OnRegisterNewDomainSuccess : Effect
    }
}


