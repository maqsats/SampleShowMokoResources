package com.dnapayments.mp.presentation.ui.features.payment_methods_detail

import com.dnapayments.mp.domain.model.payment_methods.domain.Domain
import com.dnapayments.mp.domain.model.payment_methods.setting.DetailTerminalSetting
import com.dnapayments.mp.domain.model.payment_methods.setting.PaymentMethodType
import com.dnapayments.mp.domain.model.payment_methods.setting.TerminalSetting
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState

interface DetailPaymentMethodsContract {
    sealed interface Event : UiEvent {
        data class OnInit(val paymentMethodsType: PaymentMethodType) : Event

        data class OnUnregisterDomainItem(
            val paymentMethodType: PaymentMethodType,
            val domain: Domain
        ) : Event

        data class OnChangeTerminalSetting(
            val paymentMethodType: PaymentMethodType,
            val detailTerminalSetting: DetailTerminalSetting
        ) : Event

    }

    data class State(
        val terminalSettings: ResourceUiState<List<TerminalSetting>>,
        val domainList: ResourceUiState<List<Domain>>,
        val domainUnregister: ResourceUiState<Unit>
    ) : UiState

    sealed interface Effect : UiEffect {
        data object OnUnregisterNewDomainSuccess : Effect
    }
}


