package com.dnapayments.mp.presentation.ui.features.new_payment_link

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.screenModelScope
import com.dnapayments.mp.data.model.create_new_link.CreateNewLinkRequest
import com.dnapayments.mp.data.model.create_new_link.Periodic
import com.dnapayments.mp.domain.interactors.use_cases.currency.CurrencyUseCase
import com.dnapayments.mp.domain.interactors.use_cases.ecom_stores.GetEcomStoresUseCase
import com.dnapayments.mp.domain.interactors.use_cases.stores.StoresUseCase
import com.dnapayments.mp.domain.interactors.validation.ValidateField
import com.dnapayments.mp.domain.model.currency.Currency
import com.dnapayments.mp.domain.model.online_payments.TransactionType
import com.dnapayments.mp.domain.model.stores.CardSettings
import com.dnapayments.mp.domain.network.Response.Companion.onSuccess
import com.dnapayments.mp.domain.network.toResourceUiState
import com.dnapayments.mp.domain.repository.CreateNewLinkRepository
import com.dnapayments.mp.presentation.model.ResourceUiState
import com.dnapayments.mp.presentation.model.TextFieldUiState
import com.dnapayments.mp.presentation.model.text_input.TextInput
import com.dnapayments.mp.presentation.model.validation_result.ValidationResult
import com.dnapayments.mp.presentation.mvi.BaseViewModel
import com.dnapayments.mp.utils.constants.Constants.GBP
import com.dnapayments.mp.utils.constants.Constants.UCOF
import com.dnapayments.mp.utils.extension.convertToReadable
import com.dnapayments.mp.utils.extension.convertToServerFormat
import com.dnapayments.mp.utils.extension.generateRandomOrderNumber
import kotlinx.coroutines.launch

class NewPaymentLinkViewModel(
    private val getStoresUseCase: GetEcomStoresUseCase,
    private val createNewLinkRepository: CreateNewLinkRepository,
    private val currencyUseCase: CurrencyUseCase,
    private val storesUseCase: StoresUseCase,
    private val validateField: ValidateField
) :
    BaseViewModel<NewPaymentLinkContract.Event, NewPaymentLinkContract.State, NewPaymentLinkContract.Effect>() {

    init {
        getStores()
    }

    private var cardSettings: CardSettings = CardSettings(
        allowRecurring = false,
        recurringByDefault = false,
        transactionTypeSelection = false
    )

    override fun createInitialState() = NewPaymentLinkContract.State(
        createNewLinkState = ResourceUiState.Idle,
        transactionType = TransactionType.getCreateNewLinkValues().first(),
        transactionTypeList = TransactionType.getCreateNewLinkValues(),
        storeList = ResourceUiState.Loading,
        currencyList = emptyList(),
        selectedStore = null,
        linkExpire = null,
        selectedCurrency = Currency(GBP),
        amount = TextFieldUiState(
            input = mutableStateOf(""),
            textInput = TextInput.AMOUNT,
            validationResult = mutableStateOf(ValidationResult(successful = true)),
            onFieldChanged = {
                currentState.amount.validationResult.value =
                    validateField(currentState.amount.input.value, TextInput.AMOUNT)
            }
        ),
        customerName = TextFieldUiState(
            input = mutableStateOf(""),
            textInput = TextInput.CUSTOMER_NAME,
            validationResult = mutableStateOf(ValidationResult(successful = true)),
            onFieldChanged = {
                currentState.customerName.validationResult.value =
                    validateField(currentState.customerName.input.value, TextInput.CUSTOMER_NAME)
            }
        ),
        description = TextFieldUiState(
            input = mutableStateOf(""),
            textInput = TextInput.DESCRIPTION,
            validationResult = mutableStateOf(ValidationResult(successful = true)),
            onFieldChanged = {
                currentState.description.validationResult.value =
                    validateField(currentState.description.input.value, TextInput.DESCRIPTION)
            }
        ),
        orderNumber = TextFieldUiState(
            input = mutableStateOf(generateRandomOrderNumber()),
            textInput = TextInput.ORDER_NUMBER,
            validationResult = mutableStateOf(ValidationResult(successful = true)),
            onFieldChanged = {
                currentState.orderNumber.validationResult.value =
                    validateField(currentState.orderNumber.input.value, TextInput.ORDER_NUMBER)
            }
        ),
        store = TextFieldUiState(
            input = mutableStateOf(""),
            textInput = TextInput.STORES,
            validationResult = mutableStateOf(ValidationResult(successful = true)),
            onFieldChanged = {
                currentState.store.validationResult.value =
                    validateField(currentState.store.input.value, TextInput.STORES)
            }
        ),
        expiredDate = TextFieldUiState(
            input = mutableStateOf(""),
            textInput = TextInput.EXPIRED_DATE,
            validationResult = mutableStateOf(ValidationResult(successful = true)),
            onFieldChanged = {
                currentState.expiredDate.validationResult.value =
                    validateField(currentState.expiredDate.input.value, TextInput.EXPIRED_DATE)
            }
        )
    )

    override fun handleEvent(event: NewPaymentLinkContract.Event) {
        when (event) {
            NewPaymentLinkContract.Event.OnCreateNewLinkClicked -> {
                validateAllFields()
            }
            is NewPaymentLinkContract.Event.OnCurrencyChange -> {
                setState {
                    copy(selectedCurrency = event.selectedCurrency)
                }
            }
            is NewPaymentLinkContract.Event.OnDateSelected -> {
                setState {
                    copy(
                        linkExpire = event.linkExpire
                    )
                }
                currentState.expiredDate.input.value = event.linkExpire.convertToReadable()
            }
            NewPaymentLinkContract.Event.OnGenerateNewRandomNumberClick -> {
                currentState.orderNumber.input.value = generateRandomOrderNumber()
            }
            is NewPaymentLinkContract.Event.OnStoreItemChanged -> {
                cardSettings = storesUseCase.getCardSettingsPL(
                    storesUseCase.findPrioritizedTerminal(
                        event.storesItem.terminals
                    )
                )
                val currencyList = currencyUseCase.getCurrencyListFromStoreItem(
                    event.storesItem
                )
                currentState.store.input.value = event.storesItem.name
                setState {
                    copy(
                        selectedStore = event.storesItem,
                        selectedCurrency = currencyList.first(),
                        currencyList = currencyList,
                    )

                }
            }
            is NewPaymentLinkContract.Event.OnTransactionTypeChanged -> {
                setState {
                    copy(transactionType = event.transactionType)
                }
            }
        }
    }

    private fun validateAllFields() {
        val amountValidation = validateField(currentState.amount.input.value, TextInput.AMOUNT)
        val customerNameValidation =
            validateField(currentState.customerName.input.value, TextInput.CUSTOMER_NAME)
        val descriptionValidation =
            validateField(currentState.description.input.value, TextInput.DESCRIPTION)
        val orderNumberValidation =
            validateField(currentState.orderNumber.input.value, TextInput.ORDER_NUMBER)
        val selectedDateValidation =
            validateField(
                currentState.expiredDate.input.value,
                TextInput.EXPIRED_DATE
            )

        val validations = listOf(
            amountValidation,
            customerNameValidation,
            descriptionValidation,
            orderNumberValidation,
            selectedDateValidation
        )

        val allValid = validations.all { it.successful }

        val errorTextInputFields = validations.filter { !it.successful }
        errorTextInputFields.forEach {
            when (it.textInput) {
                TextInput.AMOUNT -> {
                    currentState.amount.validationResult.value = it
                }
                TextInput.CUSTOMER_NAME -> {
                    currentState.customerName.validationResult.value = it
                }
                TextInput.DESCRIPTION -> {
                    currentState.description.validationResult.value = it
                }
                TextInput.ORDER_NUMBER -> {
                    currentState.orderNumber.validationResult.value = it
                }
                TextInput.EXPIRED_DATE -> {
                    currentState.expiredDate.validationResult.value = it
                }
                else -> {}
            }
        }

        if (allValid) {
            createNewLink()
            return
        }
    }

    private fun getStores() {
        screenModelScope.launch {
            val result = getStoresUseCase()
            setState {
                copy(
                    storeList = result.toResourceUiState()
                )
            }
        }
    }

    private fun createNewLink() {
        val createNewLink = CreateNewLinkRequest(
            amount = currentState.amount.input.value.toDouble(),
            currency = currentState.selectedCurrency.name,
            customerName = currentState.customerName.input.value,
            description = currentState.description.input.value,
            expirationDate = currentState.linkExpire.convertToServerFormat(),
            invoiceId = currentState.orderNumber.input.value,
            terminalId = currentState.selectedStore?.terminals?.first()?.id.toString(),
            periodic = if (true) Periodic(UCOF) else null,
            transactionType = if (cardSettings.transactionTypeSelection) currentState.transactionType.key else null
        )
        screenModelScope.launch {
            setState {
                copy(
                    createNewLinkState = ResourceUiState.Loading
                )
            }
            val result = createNewLinkRepository.createNewLink(createNewLink)
            setState {
                copy(
                    createNewLinkState = result.onSuccess {
                        setEffect {
                            NewPaymentLinkContract.Effect.OnSuccessCreateNewLink(it)
                        }
                    }.toResourceUiState()
                )
            }
        }
    }
}