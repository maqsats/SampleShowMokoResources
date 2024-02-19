package com.dna.payments.kmm.presentation.ui.features.new_payment_link

import com.dna.payments.kmm.data.model.stores.StoresItem
import com.dna.payments.kmm.domain.model.create_new_link.CreateNewLinkData
import com.dna.payments.kmm.domain.model.currency.Currency
import com.dna.payments.kmm.domain.model.online_payments.TransactionType
import com.dna.payments.kmm.presentation.model.ResourceUiState
import com.dna.payments.kmm.presentation.model.TextFieldUiState
import com.dna.payments.kmm.presentation.mvi.UiEffect
import com.dna.payments.kmm.presentation.mvi.UiEvent
import com.dna.payments.kmm.presentation.mvi.UiState
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