package com.dnapayments.mp.presentation.ui.features.online_payments

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.screenModelScope
import com.dnapayments.mp.data.model.search.OrderParameter
import com.dnapayments.mp.data.model.search.Paging
import com.dnapayments.mp.data.model.search.Search
import com.dnapayments.mp.data.model.search.SearchParameter
import com.dnapayments.mp.domain.interactors.page_source.OnlinePaymentsPageSource
import com.dnapayments.mp.domain.interactors.use_cases.access_level.AccessLevelUseCase
import com.dnapayments.mp.domain.interactors.use_cases.date_picker.GetDateRangeUseCase
import com.dnapayments.mp.domain.interactors.use_cases.reports.approval_average_metrics.GetReportUseCase
import com.dnapayments.mp.domain.model.date_picker.Menu
import com.dnapayments.mp.domain.model.online_payments.OnlinePaymentStatus
import com.dnapayments.mp.domain.model.permissions.AccessLevel
import com.dnapayments.mp.domain.model.permissions.Section
import com.dnapayments.mp.domain.model.search.field.Field
import com.dnapayments.mp.domain.model.search.method.MethodType
import com.dnapayments.mp.domain.model.search.type_order.TypeOrder
import com.dnapayments.mp.domain.network.Response
import com.dnapayments.mp.presentation.model.PagingUiState
import com.dnapayments.mp.presentation.mvi.BaseViewModel
import com.dnapayments.mp.utils.extension.convertToServerFormat
import com.dnapayments.mp.utils.extension.getDefaultDateRange
import kotlinx.coroutines.launch

class OnlinePaymentsViewModel(
    private val onlinePaymentsPageSource: OnlinePaymentsPageSource,
    private val getReportUseCase: GetReportUseCase,
    private val getDateRangeUseCase: GetDateRangeUseCase,
    private val accessLevelUseCase: AccessLevelUseCase,
) : BaseViewModel<OnlinePaymentsContract.Event, OnlinePaymentsContract.State, OnlinePaymentsContract.Effect>() {

    init {
        setState {
            copy(
                hasPermission =
                accessLevelUseCase.hasPermission(
                    Section.ONLINE_PAYMENTS,
                    AccessLevel.FULL
                ),
                dateRange = getDateRangeUseCase(Menu.ONLINE_PAYMENTS),
                statusList = getReportUseCase.getOrderedPaymentStatus(),
                indexOfSelectedStatus = 0
            )
        }
        onlinePaymentsPageSource.onReset()
        getOnlinePaymentList()
    }

    override fun createInitialState(): OnlinePaymentsContract.State =
        OnlinePaymentsContract.State(
            onlinePaymentList = mutableStateListOf(),
            pagingUiState = PagingUiState.Loading,
            hasPermission = false,
            selectedPage = 0,
            dateRange = getDefaultDateRange(),
            statusList = emptyList(),
            indexOfSelectedStatus = 0
        )

    override fun handleEvent(event: OnlinePaymentsContract.Event) {
        when (event) {
            is OnlinePaymentsContract.Event.OnDateSelection -> {
                setState {
                    copy(
                        dateRange = Pair(
                            event.datePickerPeriod,
                            getDateRangeUseCase(event.datePickerPeriod)
                        )
                    )
                }
                onlinePaymentsPageSource.onReset()
                getOnlinePaymentList()
            }

            is OnlinePaymentsContract.Event.OnPageChanged -> {
                setState {
                    copy(selectedPage = event.position)
                }
                setEffect {
                    OnlinePaymentsContract.Effect.OnPageChanged(event.position)
                }
            }

            is OnlinePaymentsContract.Event.OnStatusChange -> {
                setState {
                    copy(indexOfSelectedStatus = event.selectedStatusIndex)
                }
                onlinePaymentsPageSource.onReset()
                getOnlinePaymentList()
            }

            OnlinePaymentsContract.Event.OnLoadMore -> {
                if (onlinePaymentsPageSource.getIsLastPage()) return
                getOnlinePaymentList()
            }

            OnlinePaymentsContract.Event.OnRefresh -> {
                onlinePaymentsPageSource.onReset()
                getOnlinePaymentList()
            }
        }
    }

    private fun getOnlinePaymentList() {
        screenModelScope.launch {
            setState {
                copy(
                    pagingUiState = PagingUiState.Loading
                )
            }
            onlinePaymentsPageSource.updateParameters(
                formSearchBody()
            )
            val result = onlinePaymentsPageSource.onLoadMore()
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
                    onlinePaymentList = onlinePaymentsPageSource.remoteData
                )
            }
        }
    }

    private fun formSearchBody() =
        Search(
            orderParameters = listOf(
                OrderParameter(
                    field = Field.CREATED_DATE.value,
                    typeOrder = TypeOrder.DESC.name
                ),
                OrderParameter(
                    field = Field.ID.value,
                    typeOrder = TypeOrder.DESC.name
                )
            ),
            paging = Paging(
                0,
                0
            ),
            searchParameters = formSearchParameters()
        )

    private fun formSearchParameters(): List<SearchParameter> {
        val searchParameters = mutableListOf<SearchParameter>()
        searchParameters.add(
            SearchParameter(
                name = Field.CREATED_DATE.value,
                method = MethodType.BETWEEN.value,
                searchParameter = listOf(
                    currentState.dateRange.second.startDate.convertToServerFormat(),
                    currentState.dateRange.second.endDate.convertToServerFormat()
                )
            )
        )

        if (currentState.statusList[currentState.indexOfSelectedStatus] == OnlinePaymentStatus.ALL) {
            return searchParameters
        }

        searchParameters.add(
            SearchParameter(
                name = Field.STATUS_ID.value,
                method = MethodType.EQUAL.value,
                searchParameter = listOf(
                    currentState.statusList[currentState.indexOfSelectedStatus].name
                )
            )
        )
        return searchParameters
    }
}
