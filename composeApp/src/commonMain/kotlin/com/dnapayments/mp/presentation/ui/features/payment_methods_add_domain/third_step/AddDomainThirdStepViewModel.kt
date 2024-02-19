package com.dnapayments.mp.presentation.ui.features.payment_methods_add_domain.third_step

import cafe.adriel.voyager.core.model.screenModelScope
import com.dnapayments.mp.data.model.payment_methods.RegistrationDomainRequest
import com.dnapayments.mp.domain.interactors.use_cases.payment_method.RegisterDomainUseCase
import com.dnapayments.mp.domain.model.payment_methods.setting.PaymentMethodType
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.mvi.BaseViewModel
import com.dnapayments.mp.utils.UiText
import kotlinx.coroutines.launch

class AddDomainThirdStepViewModel(
    private val registerDomainUseCase: RegisterDomainUseCase
) : BaseViewModel<AddDomainThirdStepContract.Event, AddDomainThirdStepContract.State, AddDomainThirdStepContract.Effect>() {

    override fun createInitialState(): AddDomainThirdStepContract.State =
        AddDomainThirdStepContract.State(
            addDomainState = ResourceUiState.Idle
        )

    override fun handleEvent(event: AddDomainThirdStepContract.Event) {
        when (event) {
            is AddDomainThirdStepContract.Event.OnAddNewDomain -> {
                addNewDomain(event.domain, event.paymentMethodType)
            }
        }
    }

    private fun addNewDomain(domain: String, paymentMethodType: PaymentMethodType) {
        setState { copy(addDomainState = ResourceUiState.Loading) }
        screenModelScope.launch {
            val result = registerDomainUseCase(paymentMethodType = paymentMethodType,
                registerDomainRequest = RegistrationDomainRequest(
                    mutableListOf<String>().apply { add(domain) }
                ))
            setState {
                copy(
                    addDomainState = when (result) {
                        is Response.Success -> {
                            setEffect {
                                AddDomainThirdStepContract.Effect.OnRegisterNewDomainSuccess
                            }
                            ResourceUiState.Success(
                                result.data
                            )
                        }

                        is Response.Error -> {
                            ResourceUiState.Error(result.error)
                        }

                        is Response.NetworkError -> {
                            ResourceUiState.Error(UiText.DynamicString("Network error"))
                        }

                        is Response.TokenExpire -> {
                            ResourceUiState.Error(UiText.DynamicString("Token expired"))
                        }
                    }
                )
            }
        }
    }
}
