package com.dna.payments.kmm.presentation.ui.features.payment_links

import cafe.adriel.voyager.core.model.screenModelScope
import com.dna.payments.kmm.domain.interactors.use_cases.access_level.AccessLevelUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.GetDateRangeUseCase
import com.dna.payments.kmm.domain.model.date_picker.Menu
import com.dna.payments.kmm.domain.model.payment_links.PaymentLinkStatus
import com.dna.payments.kmm.domain.model.payment_links.PaymentLinksSearchParameters
import com.dna.payments.kmm.domain.model.permissions.AccessLevel
import com.dna.payments.kmm.domain.model.permissions.Section
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.use_case.PaymentLinkStatusUseCase
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import com.dna.payments.kmm.utils.UiText
import com.dna.payments.kmm.utils.extension.convertToServerFormat
import com.dna.payments.kmm.utils.extension.getDefaultDateRange
import kotlinx.coroutines.launch

class PaymentLinksViewModel(
    private val paymentLinksPageSource: PaymentLinksPageSource,
    private val accessLevelUseCase: AccessLevelUseCase,
    private val getDateRangeUseCase: GetDateRangeUseCase,
    private val paymentLinkStatusUseCase: PaymentLinkStatusUseCase
) : BaseViewModel<PaymentLinksContract.Event, PaymentLinksContract.State, PaymentLinksContract.Effect>() {

    private var paymentLinkStatusList = paymentLinkStatusUseCase.getMainPaymentLinkStatus()
    private var selectedPaymentLinkStatus = paymentLinkStatusList.first()

    init {
        setState {
            copy(
                hasPermission =
                accessLevelUseCase.hasPermission(
                    Section.PAYMENT_LINKS,
                    AccessLevel.FULL
                ),
                dateRange = getDateRangeUseCase(Menu.OVERVIEW)
            )
        }
    }

    override fun createInitialState(): PaymentLinksContract.State =
        PaymentLinksContract.State(
            paymentLinkList = ResourceUiState.Idle,
            hasPermission = false,
            selectedPage = 0,
            dateRange = getDefaultDateRange()
        )

    override fun handleEvent(event: PaymentLinksContract.Event) {
        when (event) {
            is PaymentLinksContract.Event.OnInit -> {
                paymentLinksPageSource.onReset()
                getPaymentLinkList()
            }

            is PaymentLinksContract.Event.OnPageChanged -> {
                setState {
                    copy(selectedPage = event.position)
                }
                setEffect {
                    PaymentLinksContract.Effect.OnPageChanged(event.position)
                }
            }

            is PaymentLinksContract.Event.OnDateSelection -> {
                setState {
                    copy(
                        dateRange = Pair(
                            event.datePickerPeriod,
                            getDateRangeUseCase(event.datePickerPeriod)
                        )
                    )
                }
                paymentLinksPageSource.onReset()
                getPaymentLinkList()
            }
        }
    }

    private fun getPaymentLinkList() {
        setState { copy(paymentLinkList = ResourceUiState.Loading) }
        screenModelScope.launch {
            paymentLinksPageSource.updateParameters(
                PaymentLinksSearchParameters(
                    startDate = currentState.dateRange.second.startDate.convertToServerFormat(),
                    endDate = currentState.dateRange.second.endDate.convertToServerFormat(),
                    status = if (selectedPaymentLinkStatus == PaymentLinkStatus.ALL)
                        "" else selectedPaymentLinkStatus.value
                )
            )
            val result = paymentLinksPageSource.onLoadMore()
            setState {
                copy(
                    paymentLinkList = when (result) {
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
}
