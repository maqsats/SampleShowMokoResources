package com.dna.payments.kmm.presentation.ui.features.online_payments

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.screenModelScope
import com.dna.payments.kmm.data.model.search.OrderParameter
import com.dna.payments.kmm.data.model.search.Paging
import com.dna.payments.kmm.data.model.search.Search
import com.dna.payments.kmm.data.model.search.SearchParameter
import com.dna.payments.kmm.domain.interactors.use_cases.access_level.AccessLevelUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.GetDateRangeUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.transaction.TransactionUseCase
import com.dna.payments.kmm.domain.model.date_picker.Menu
import com.dna.payments.kmm.domain.model.permissions.AccessLevel
import com.dna.payments.kmm.domain.model.permissions.Section
import com.dna.payments.kmm.domain.model.search.field.Field
import com.dna.payments.kmm.domain.model.search.method.MethodType
import com.dna.payments.kmm.domain.model.search.type_order.TypeOrder
import com.dna.payments.kmm.domain.model.status_summary.PaymentStatus
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.domain.interactors.page_source.OnlinePaymentsPageSource
import com.dna.payments.kmm.presentation.model.PagingUiState
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import com.dna.payments.kmm.utils.extension.convertToServerFormat
import com.dna.payments.kmm.utils.extension.getDefaultDateRange
import kotlinx.coroutines.launch

class OnlinePaymentsViewModel(
    private val onlinePaymentsPageSource: OnlinePaymentsPageSource,
    private val transactionUseCase: TransactionUseCase,
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
                statusList = transactionUseCase.getOrderedPaymentStatus(),
                indexOfSelectedStatus = 0
            )
        }
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

            OnlinePaymentsContract.Event.OnInit -> {
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

        if (currentState.statusList[currentState.indexOfSelectedStatus] == PaymentStatus.ALL) {
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
