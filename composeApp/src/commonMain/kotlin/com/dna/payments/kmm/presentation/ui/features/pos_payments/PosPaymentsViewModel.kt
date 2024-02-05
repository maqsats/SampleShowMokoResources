package com.dna.payments.kmm.presentation.ui.features.pos_payments

import androidx.compose.runtime.mutableStateListOf
import cafe.adriel.voyager.core.model.screenModelScope
import com.dna.payments.kmm.data.model.transactions.pos.PosRequestParam
import com.dna.payments.kmm.domain.interactors.page_source.PosPaymentsPageSource
import com.dna.payments.kmm.domain.interactors.use_cases.access_level.AccessLevelUseCase
import com.dna.payments.kmm.domain.interactors.use_cases.date_picker.GetDateRangeUseCase
import com.dna.payments.kmm.domain.model.date_picker.Menu
import com.dna.payments.kmm.domain.model.permissions.AccessLevel
import com.dna.payments.kmm.domain.model.permissions.Section
import com.dna.payments.kmm.domain.model.pos_payments.PosPaymentStatusV2
import com.dna.payments.kmm.domain.network.Response
import com.dna.payments.kmm.presentation.model.PagingUiState
import com.dna.payments.kmm.presentation.mvi.BaseViewModel
import com.dna.payments.kmm.utils.extension.convertToServerFormat
import com.dna.payments.kmm.utils.extension.getDefaultDateRange
import kotlinx.coroutines.launch

class PosPaymentsViewModel(
    private val posPaymentsPageSource: PosPaymentsPageSource,
    private val getDateRangeUseCase: GetDateRangeUseCase,
    private val accessLevelUseCase: AccessLevelUseCase,
) : BaseViewModel<PosPaymentsContract.Event, PosPaymentsContract.State, PosPaymentsContract.Effect>() {

    init {
        setState {
            copy(
                hasPermission =
                accessLevelUseCase.hasPermission(
                    Section.POS_PAYMENTS,
                    AccessLevel.FULL
                ),
                dateRange = getDateRangeUseCase(Menu.POS_PAYMENTS)
            )
        }
        posPaymentsPageSource.onReset()
        getPosPaymentList()
    }

    override fun createInitialState(): PosPaymentsContract.State =
        PosPaymentsContract.State(
            posPaymentList = mutableStateListOf(),
            pagingUiState = PagingUiState.Loading,
            hasPermission = false,
            selectedPage = 0,
            dateRange = getDefaultDateRange(),
            statusList = PosPaymentStatusV2.entries,
            selectedStatus = PosPaymentStatusV2.ALL,
        )

    override fun handleEvent(event: PosPaymentsContract.Event) {
        when (event) {
            is PosPaymentsContract.Event.OnDateSelection -> {
                setState {
                    copy(
                        dateRange = Pair(
                            event.datePickerPeriod,
                            getDateRangeUseCase(event.datePickerPeriod)
                        )
                    )
                }
                posPaymentsPageSource.onReset()
                getPosPaymentList()
            }

            is PosPaymentsContract.Event.OnPageChanged -> {
                setState {
                    copy(selectedPage = event.position)
                }
                setEffect {
                    PosPaymentsContract.Effect.OnPageChanged(event.position)
                }
            }

            is PosPaymentsContract.Event.OnStatusChange -> {
                setState {
                    copy(selectedStatus = event.selectedStatus)
                }
                posPaymentsPageSource.onReset()
                getPosPaymentList()
            }

            PosPaymentsContract.Event.OnLoadMore -> {
                if (posPaymentsPageSource.getIsLastPage()) return
                getPosPaymentList()
            }

            PosPaymentsContract.Event.OnRefresh -> {
                posPaymentsPageSource.onReset()
                getPosPaymentList()
            }
        }
    }

    private fun getPosPaymentList() {
        screenModelScope.launch {
            setState {
                copy(
                    pagingUiState = PagingUiState.Loading
                )
            }
            posPaymentsPageSource.updateParameters(
                PosRequestParam(
                    startDate = currentState.dateRange.second.startDate.convertToServerFormat(),
                    endDate = currentState.dateRange.second.endDate.convertToServerFormat(),
                    status = currentState.selectedStatus.key,
                    page = 0,
                    size = 0
                )
            )
            val result = posPaymentsPageSource.onLoadMore()
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
                    posPaymentList = posPaymentsPageSource.remoteData
                )
            }
        }
    }
}
