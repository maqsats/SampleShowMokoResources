package com.dnapayments.mp.presentation.ui.features.new_payment_link

import com.dnapayments.mp.data.model.stores.StoresItem
import com.dnapayments.mp.domain.model.create_new_link.CreateNewLinkData
import com.dnapayments.mp.domain.model.currency.Currency
import com.dnapayments.mp.domain.model.online_payments.TransactionType
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.model.TextFieldUiState
import com.dnapayments.mp.presentation.mvi.UiEffect
import com.dnapayments.mp.presentation.mvi.UiEvent
import com.dnapayments.mp.presentation.mvi.UiState
import korlibs.time.DateTime

interface NewPaymentLinkContract {

    sealed interface Event : UiEvent {
        data object OnCreateNewLinkClicked : Event
        data class OnDateSelected(val linkExpire: DateTime) : Event
        data class OnCurrencyChange(val selectedCurrency: Currency) : Event
        data class OnStoreItemChanged(val storesItem: StoresItem) : Event
        data object OnGenerateNewRandomNumberClick : Event
        data class OnTransactionTypeChanged(val transactionType: TransactionType) : Event
    }

    data class State(
        val createNewLinkState: ResourceUiState<CreateNewLinkData>,
        val linkExpire: DateTime?,
        val storeList: ResourceUiState<List<StoresItem>>,
        val selectedStore: StoresItem?,
        val transactionType: TransactionType,
        val transactionTypeList: List<TransactionType>,
        val selectedCurrency: Currency,
        val currencyList: List<Currency>,
        val amount: TextFieldUiState,
        val customerName: TextFieldUiState,
        val description: TextFieldUiState,
        val orderNumber: TextFieldUiState,
        val expiredDate: TextFieldUiState,
        val store: TextFieldUiState,
    ) : UiState

    sealed interface Effect : UiEffect {
        data class OnSuccessCreateNewLink(val createNewLinkData: CreateNewLinkData) : Effect
    }
}