package com.dna.payments.kmm.presentation.ui.features.payment_methods_detail

import cafe.adriel.voyager.core.model.coroutineScope
import com.dna.payments.kmm.domain.interactors.use_cases.access_level.AccessLevelUseCase
import com.dna.payments.kmm.domain.model.payment_methods.setting.PaymentMethodType
import com.dna.payments.kmm.domain.model.permissions.AccessLevel
import com.dna.payments.kmm.domain.model.permissions.Section
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.use_case.GetDomainsUseCase
import com.dna.payments.kmm.domain.use_case.GetTerminalSettingsUseCase
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import com.dna.payments.kmm.utils.UiText
import kotlinx.coroutines.launch

class DetailPaymentMethodsViewModel(
    private val getTerminalSettingsUseCase: GetTerminalSettingsUseCase,
    private val accessLevelUseCase: AccessLevelUseCase,
    private val getDomainsUseCase: GetDomainsUseCase
) : BaseViewModel<DetailPaymentMethodsContract.Event, DetailPaymentMethodsContract.State, DetailPaymentMethodsContract.Effect>() {

    override fun createInitialState(): DetailPaymentMethodsContract.State =
        DetailPaymentMethodsContract.State(
            terminalSettings = ResourceUiState.Idle,
            domainList = ResourceUiState.Idle
        )

    override fun handleEvent(event: DetailPaymentMethodsContract.Event) {
        when (event) {
            is DetailPaymentMethodsContract.Event.OnInit -> {
                fetchData(event.paymentMethodsType)
                getDomainList(event.paymentMethodsType)
            }
        }
    }

    private fun fetchData(paymentMethodType: PaymentMethodType) {
        setState { copy(terminalSettings = ResourceUiState.Loading) }
            coroutineScope.launch {
                val result = getTerminalSettingsUseCase(paymentMethodType)
                setState {
                    copy(
                        terminalSettings = when (result) {
                            is Response.Success -> {
                                ResourceUiState.Success(result.data)
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

    private fun getDomainList(
        paymentMethodType: PaymentMethodType
    ) {
        setState { copy(domainList = ResourceUiState.Loading) }
        coroutineScope.launch {
            val result = getDomainsUseCase(paymentMethodType)
            setState {
                copy(
                    domainList = when (result) {
                        is Response.Success -> {
                            ResourceUiState.Success(result.data.map {
                                it.copy(
                                    isDeleteAvailable = accessLevelUseCase.hasPermission(
                                        Section.PAYMENT_METHODS,
                                        AccessLevel.FULL
                                    )
                                )
                            })
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
