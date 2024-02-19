package com.dnapayments.mp.presentation.ui.features.payment_links

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.screenModelScope
import com.dnapayments.mp.domain.interactors.use_cases.access_level.AccessLevelUseCase
import com.dnapayments.mp.domain.interactors.use_cases.date_picker.GetDateRangeUseCase
import com.dnapayments.mp.domain.model.date_picker.Menu
import com.dnapayments.mp.domain.model.payment_links.PaymentLinkStatus
import com.dnapayments.mp.domain.model.payment_links.PaymentLinksSearchParameters
import com.dnapayments.mp.domain.model.permissions.AccessLevel
import com.dnapayments.mp.domain.model.permissions.Section
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.domain.use_case.PaymentLinkStatusUseCase
import com.dnapayments.mp.presentation.model.PagingUiState
import com.dnapayments.mp.presentation.mvi.BaseViewModel
import com.dnapayments.mp.utils.extension.convertToServerFormat
import com.dnapayments.mp.utils.extension.getDefaultDateRange
import kotlinx.coroutines.launch

class PaymentLinksViewModel(
    private val paymentLinksPageSource: PaymentLinksPageSource,
    private val accessLevelUseCase: AccessLevelUseCase,
    private val getDateRangeUseCase: GetDateRangeUseCase,
    private val paymentLinkStatusUseCase: PaymentLinkStatusUseCase
) : BaseViewModel<PaymentLinksContract.Event, PaymentLinksContract.State, PaymentLinksContract.Effect>() {

    init {
        setState {
            copy(
                hasPermission =
                accessLevelUseCase.hasPermission(
                    Section.PAYMENT_LINKS,
                    AccessLevel.FULL
                ),
                dateRange = getDateRangeUseCase(Menu.PAYMENT_LINKS),
                statusList = paymentLinkStatusUseCase.getMainPaymentLinkStatus(),
                indexOfSelectedStatus = 0
            )
        }
        paymentLinksPageSource.onReset()
        getPaymentLinkList()
    }

    override fun createInitialState(): PaymentLinksContract.State =
        PaymentLinksContract.State(
            paymentLinkList = mutableStateListOf(),
            pagingUiState = PagingUiState.Loading,
            hasPermission = false,
            selectedPage = 0,
            dateRange = getDefaultDateRange(),
            statusList = emptyList(),
            indexOfSelectedStatus = 0
        )

    override fun handleEvent(event: PaymentLinksContract.Event) {
        when (event) {
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

            is PaymentLinksContract.Event.OnStatusChange -> {
                setState {
                    copy(indexOfSelectedStatus = event.selectedStatusIndex)
                }
                paymentLinksPageSource.onReset()
                getPaymentLinkList()
            }
            PaymentLinksContract.Event.OnLoadMore -> {
                if (paymentLinksPageSource.getIsLastPage()) return
                getPaymentLinkList()
            }
            PaymentLinksContract.Event.OnRefresh -> {
                paymentLinksPageSource.onReset()
                getPaymentLinkList()
            }
        }
    }

    private fun getPaymentLinkList() {
        screenModelScope.launch {
            setState {
                copy(
                    pagingUiState = PagingUiState.Loading
                )
            }
            paymentLinksPageSource.updateParameters(
                PaymentLinksSearchParameters(
                    startDate = currentState.dateRange.second.startDate.convertToServerFormat(),
                    endDate = currentState.dateRange.second.endDate.convertToServerFormat(),
                    status = if (currentState.statusList[currentState.indexOfSelectedStatus] == PaymentLinkStatus.ALL)
                        "" else currentState.statusList[currentState.indexOfSelectedStatus].value
                )
            )
            val result = paymentLinksPageSource.onLoadMore()

            setState {
                copy(
                    pagingUiState = when (result) {
                        is Response.Success -> {
                            PagingUiState.Idle
                        }
                        is Response.Error -> {
                            PagingUiState.Error(result.error)
                        }
                        is Response.NetworkError -> {
                            PagingUiState.NetworkError
                        }
                        is Response.TokenExpire -> {
                            PagingUiState.TokenExpire
                        }
                    },
                    paymentLinkList = paymentLinksPageSource.remoteData
                )
            }
        }
    }
}
